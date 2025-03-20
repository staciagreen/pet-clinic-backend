package PetBase;


import PetBase.config.HibernateUtil;
import PetBase.dao.PetDaoImpl;
import PetBase.entity.Pet;
import PetBase.service.PetService;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        PetDaoImpl petDao = new PetDaoImpl(sessionFactory);
        PetService petService = new PetService(petDao);

        Pet pet = new Pet();
        pet.setName("Барсик");
        pet.setBirthDate("2023-01-01");
        pet.setBreed("Сиамский");
        pet.setColor("Белый");

        petService.addPet(pet);
        petService.getAllPets().forEach(p -> System.out.println(p.getName()));
    }
}
