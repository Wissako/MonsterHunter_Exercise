package com.luis.proyectofinal_luisalfonso.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.luis.proyectofinal_luisalfonso.models.entities.*;
import com.luis.proyectofinal_luisalfonso.models.enums.*;
import com.luis.proyectofinal_luisalfonso.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(
            MonsterRepository monsterRepo,
            HabitatRepository habitatRepo,
            MaterialRepository materialRepo,
            HunterRepository hunterRepo,
            QuestRepository questRepo
    ) {
        return args -> {
            // -----------------------------------------------------------
            // 1. CREAR HÁBITATS INICIALES (Si no existen)
            // -----------------------------------------------------------
            if (habitatRepo.count() == 0) {
                habitatRepo.save(Habitat.builder().zone(HabitatName.ANCIENT_FOREST).weather(HabitatWeather.RAINY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.VOLCANO).weather(HabitatWeather.SUNNY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.HOARFROST_REACH).weather(HabitatWeather.SNOWY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.ROTTEN_VALE).weather(HabitatWeather.WINDY).build());
                System.out.println("✅ Hábitats iniciales creados.");
            }
            List<Habitat> allHabitats = habitatRepo.findAll();

            // -----------------------------------------------------------
            // 2. CARGAR MONSTRUOS DESDE JSON
            // -----------------------------------------------------------
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = TypeReference.class.getResourceAsStream("/data/monsters.json");

            try {
                if (inputStream == null) {
                    System.out.println("⚠️ ERROR: No se encuentra el archivo /data/monsters.json");
                } else {
                    // Convertimos el JSON a una lista de objetos Java temporales (DTO)
                    List<MonsterJsonDto> monstersFromJson = mapper.readValue(inputStream, new TypeReference<List<MonsterJsonDto>>(){});

                    for (MonsterJsonDto dto : monstersFromJson) {
                        // Evitamos duplicados por nombre
                        if (monsterRepo.findByNameContainingIgnoreCase(dto.name).isEmpty()) {

                            Monster monster = new Monster();
                            monster.setName(dto.name);
                            monster.setType(dto.type);
                            monster.setElement(dto.element);
                            monster.setWeakness(dto.weakness);
                            monster.setThreatLevel(dto.threatLevel);

                            // A) Procesar Materiales (Drops)
                            List<Material> materials = new ArrayList<>();
                            if (dto.drops != null) {
                                for (String matName : dto.drops) {
                                    // Creamos el material
                                    Material m = new Material();
                                    m.setName(matName);
                                    m.setValue(dto.threatLevel * 150.0);
                                    m.setRarity(dto.threatLevel > 7 ? MaterialRarity.RARE : MaterialRarity.COMMON);
                                    materialRepo.save(m); //Guardar antes de asignar
                                    materials.add(m);
                                }
                            }
                            monster.setDrops(materials);

                            // B) Procesar Hábitats
                            List<Habitat> assignedHabitats = new ArrayList<>();
                            if (dto.habitats != null) {
                                for (String hName : dto.habitats) {
                                    // Buscamos en la BBDD uno que coincida con el nombre del JSON
                                    Optional<Habitat> match = allHabitats.stream()
                                            .filter(h -> h.getZone().name().equalsIgnoreCase(hName))
                                            .findFirst();
                                    match.ifPresent(assignedHabitats::add);
                                }
                            }
                            // Si no encuentra hábitat, le ponemos uno por defecto para que no falle
                            if (assignedHabitats.isEmpty()) assignedHabitats.add(allHabitats.get(0));

                            monster.setHabitats(assignedHabitats);

                            monsterRepo.save(monster);
                        }
                    }
                    System.out.println("🐉 JSON CARGADO: " + monstersFromJson.size() + " monstruos añadidos.");
                }
            } catch (Exception e) {
                System.out.println("❌ Error leyendo JSON: " + e.getMessage());
                e.printStackTrace();
            }

            // -----------------------------------------------------------
            // 3. DATOS EXTRA (Para probar la web luego)
            // -----------------------------------------------------------
            if (hunterRepo.count() == 0) {
                Hunter h = new Hunter();
                h.setName("LuisHunter");
                h.setEmail("luis@guild.com");
                h.setRank(99);
                h.setMainWeapon(HunterWeapons.LONG_SWORD);
                hunterRepo.save(h);
            }
        };
    }

    // AUX JSON DTO
    static class MonsterJsonDto {
        public String name;
        public String type;
        public String element;
        public String weakness;
        public Integer threatLevel;
        public List<String> drops;
        public List<String> habitats;
    }
}