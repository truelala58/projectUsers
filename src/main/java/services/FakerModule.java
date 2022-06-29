package services;

import com.github.javafaker.Faker;
import com.google.inject.Binder;
import com.google.inject.Module;

public class FakerModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(Faker.class).asEagerSingleton();
    }
}
