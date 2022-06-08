package services;

import com.google.inject.Binder;
import com.google.inject.Module;

public class UserModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(UserClient.class).asEagerSingleton();
    }
}
