package com.Timepass.ecotrack.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.Timepass.ecotrack.entities.Car;
import com.Timepass.ecotrack.entities.Role;
import com.Timepass.ecotrack.entities.User;
import com.Timepass.ecotrack.enums.AppRole;
import com.Timepass.ecotrack.repository.CarRepository;
import com.Timepass.ecotrack.repository.RoleRepository;
import com.Timepass.ecotrack.repository.UserRespository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final CarRepository carRepository;
    private final UserRespository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(RoleRepository roleRepository, CarRepository carRepository,
            UserRespository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.carRepository = carRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository.save(new Role(null, AppRole.ROLE_USER, null));
            roleRepository.save(new Role(null, AppRole.ROLE_ADMIN, null));
        }

        if (userRepository.findByEmail("admin@urbandrive.com").isEmpty()) {
            Role adminRole = roleRepository.findByRoleName(AppRole.ROLE_ADMIN).orElseThrow();
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@urbandrive.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(adminRole);
            userRepository.save(admin);
        }

        if (carRepository.count() == 0) {
            List<Car> cars = Arrays.asList(
                createCar("BMW XM", "Luxury SUV", 15000.0, "https://4kwallpapers.com/images/wallpapers/bmw-xm-8k-3840x2160-12322.jpg"),
                createCar("Audi A3", "Sedan", 13200.0, "https://images2.alphacoders.com/120/1209529.jpg"),
                createCar("Mercedes S-Class", "Luxury Sedan", 5000.0, "https://4kwallpapers.com/images/wallpapers/mercedes-benz-s-500-3840x2160-25482.jpg"),
                createCar("Ferrari 488", "Sports", 50000.0, "https://images8.alphacoders.com/697/thumb-1920-697316.jpeg"),
                createCar("Toyota Fortuner", "SUV", 3500.0, "https://e0.pxfuel.com/wallpapers/345/92/desktop-wallpaper-toyota-fortuner-looks-gorgeous-in-all-black-exterior-toyota-fortuner-2021.jpg"),
                createCar("Hyundai Creta", "Compact SUV", 2500.0, "https://www.v3cars.com/media/model-imgs/1705404458-02-creta-front-quater-exterior.webp"),
                createCar("Mercedes-AMG G 63", "SUV", 18000.0, "https://4kwallpapers.com/images/wallpapers/urban-automotive-1920x1200-16359.jpg"),
                createCar("Porsche 911", "Sports", 12000.0, "https://wallpapercave.com/wp/wp12419034.jpg"),
                createCar("Lamborghini Huracan", "Sports", 15000.0, "https://www.hdwallpapers.in/download/2017_lamborghini_huracan_lp610_4_avio_4k-2560x1600.jpg"),
                createCar("VW Virtus", "Sedan", 1500.0, "https://spn-sta.spinny.com/blog/20220308152631/VW-Virtus-launch.jpg")
            );
            carRepository.saveAll(cars);
        }
    }

    private Car createCar(String name, String type, Double price, String image) {
        Car car = new Car();
        car.setName(name);
        car.setType(type);
        car.setPricePerDay(price);
        car.setImageUrl(image);
        car.setAvailable(true);
        return car;
    }
}
