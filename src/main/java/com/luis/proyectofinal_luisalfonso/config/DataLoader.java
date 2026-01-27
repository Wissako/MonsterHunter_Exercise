package com.luis.proyectofinal_luisalfonso.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luis.proyectofinal_luisalfonso.models.entities.*;
import com.luis.proyectofinal_luisalfonso.models.enums.*;
import com.luis.proyectofinal_luisalfonso.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet; // Necesario para la conversión
import java.util.List;

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
            ObjectMapper mapper = new ObjectMapper();

            // 1. HÁBITATS BÁSICOS
            if (habitatRepo.count() == 0) {
                habitatRepo.save(Habitat.builder().zone(HabitatName.ANCIENT_FOREST).weather(HabitatWeather.RAINY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.VOLCANO).weather(HabitatWeather.SUNNY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.HOARFROST_REACH).weather(HabitatWeather.SNOWY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.ROTTEN_VALE).weather(HabitatWeather.WINDY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.CORAL_HIGHLANDS).weather(HabitatWeather.SUNNY).build());
                habitatRepo.save(Habitat.builder().zone(HabitatName.WILDSPIRE_WASTE).weather(HabitatWeather.SUNNY).build());
            }
            List<Habitat> allHabitats = habitatRepo.findAll();

            // 2. CARGAR MONSTRUOS
            try {
                InputStream inputStream = TypeReference.class.getResourceAsStream("/data/monsters.json");
                if (inputStream != null) {
                    List<MonsterJsonDto> monsters = mapper.readValue(inputStream, new TypeReference<List<MonsterJsonDto>>(){});

                    for (MonsterJsonDto dto : monsters) {
                        if (monsterRepo.findByNameContainingIgnoreCase(dto.name).isEmpty()) {
                            Monster m = new Monster();
                            m.setName(dto.name);
                            m.setType(dto.type);
                            m.setElement(dto.element);
                            m.setWeakness(dto.weakness);
                            m.setThreatLevel(dto.threatLevel);

                            // Procesar Materiales
                            List<Material> tempMaterials = new ArrayList<>();
                            if(dto.drops != null) {
                                for(String mat : dto.drops) {
                                    Material material = new Material();
                                    material.setName(mat);
                                    material.setValue(200.0);
                                    material.setRarity(MaterialRarity.COMMON);
                                    materialRepo.save(material);
                                    tempMaterials.add(material);
                                }
                            }
                            // CONVERSIÓN LIST -> SET
                            m.setDrops(new HashSet<>(tempMaterials));

                            // Procesar Hábitats
                            List<Habitat> tempHabitats = new ArrayList<>();
                            if(dto.habitats != null){
                                for(String hName : dto.habitats){
                                    allHabitats.stream()
                                            .filter(h -> h.getZone().name().equalsIgnoreCase(hName))
                                            .findFirst()
                                            .ifPresent(tempHabitats::add);
                                }
                            }
                            if(tempHabitats.isEmpty()) tempHabitats.add(allHabitats.get(0));

                            // CONVERSIÓN LIST -> SET
                            m.setHabitats(new HashSet<>(tempHabitats));

                            monsterRepo.save(m);
                        }
                    }
                    System.out.println("✅ Monstruos cargados.");
                }
            } catch (Exception e) {
                System.err.println("❌ Error en monsters.json: " + e.getMessage());
            }

            // 3. CARGAR MISIONES
            try {
                InputStream inputQuest = TypeReference.class.getResourceAsStream("/data/quests.json");
                if (inputQuest != null) {
                    List<QuestJsonDto> quests = mapper.readValue(inputQuest, new TypeReference<List<QuestJsonDto>>(){});
                    for (QuestJsonDto qDto : quests) {
                        List<Monster> targets = monsterRepo.findByNameContainingIgnoreCase(qDto.target);
                        if (!targets.isEmpty()) {
                            Quest q = new Quest();
                            q.setName(qDto.name);
                            q.setDifficulty(qDto.difficulty);
                            q.setReward(qDto.reward);
                            q.setTarget(targets.get(0));
                            questRepo.save(q);
                        }
                    }
                    System.out.println("✅ Misiones cargadas.");
                }
            } catch (Exception e) {
                System.err.println("❌ Error en quests.json: " + e.getMessage());
            }
        };
    }

    static class MonsterJsonDto {
        public String name, type, element, weakness;
        public Integer threatLevel;
        public List<String> drops, habitats;
    }
    static class QuestJsonDto {
        public String name, target;
        public Integer difficulty;
        public Double reward;
    }
}