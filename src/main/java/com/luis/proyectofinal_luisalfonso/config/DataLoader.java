package com.luis.proyectofinal_luisalfonso.config;

import com.luis.proyectofinal_luisalfonso.models.entities.*;
import com.luis.proyectofinal_luisalfonso.models.enums.HabitatName;
import com.luis.proyectofinal_luisalfonso.models.enums.HabitatWeather;
import com.luis.proyectofinal_luisalfonso.models.enums.HunterWeapons;
import com.luis.proyectofinal_luisalfonso.models.enums.MaterialRarity;
import com.luis.proyectofinal_luisalfonso.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {
@Bean
    CommandLineRunner initData(
            MonsterRepository monsterRepository,
            HabitatRepository habitatRepository,
            MaterialRepository materialRepository,
            QuestRepository questRepository,
            HunterRepository hunterRepository
){
  return args -> {
      //1º crear habitats
        Habitat forest = new Habitat();
            forest.setZone(HabitatName.ANCIENT_FOREST);
            forest.setWeather(HabitatWeather.RAINY);

        Habitat volcano = new Habitat();
            volcano.setZone(HabitatName.VOLCANO);
            volcano.setWeather(HabitatWeather.SUNNY);

        habitatRepository.saveAll(List.of(forest, volcano));
        //Crear Materiales
        Material rathScale = new Material();
            rathScale.setName("Rathalos Scale");
            rathScale.setValue(500);
            rathScale.setRarity(MaterialRarity.RARE);

        Material rathShell = new Material();
            rathShell.setName("Rathalos Shell");
            rathShell.setValue(300);
            rathShell.setRarity(MaterialRarity.COMMON);
        materialRepository.saveAll(List.of(rathScale, rathShell));

        //Crear Monstruos
        Monster rathalos = new Monster();
            rathalos.setName("Rathalos");
            rathalos.setWeakness("Dragon");
            rathalos.setType("Flying Wyvern");
            rathalos.setHabitats(List.of(forest, volcano));
            rathalos.setDrops(List.of(rathScale, rathShell));
        monsterRepository.save(rathalos);

        //Cazador de prueba
       if(hunterRepository.count()==0){
           Hunter ayako = new Hunter();
              ayako.setName("Ayako");
              ayako.setRank(5);
              ayako.setEmail("luis@gremio.com");
              ayako.setMainWeapon(HunterWeapons.LIGHT_BOWGUN);
           Hunter hanako = new Hunter();
              hanako.setName("Hanako");
              hanako.setRank(7);
              hanako.setEmail("ivan@gremio.com");
              hanako.setMainWeapon(HunterWeapons.HAMMER);
           hunterRepository.saveAll(List.of(ayako, hanako));
       }

       //Crear Mision
      Quest quest = new Quest();
       quest.setName("Slay the king of the skies");
       quest.setDifficulty(4);
       quest.setReward(1500.0);
       quest.setTarget(rathalos);

       questRepository.save(quest);

      System.out.println("Data Saved Successfully");
  };
}
}
