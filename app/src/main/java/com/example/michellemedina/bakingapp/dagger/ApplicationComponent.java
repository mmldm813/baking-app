package com.example.michellemedina.bakingapp.dagger;

import com.example.michellemedina.bakingapp.main.RecipeActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = ApplicationModule.class)
@Singleton
public interface ApplicationComponent {
    void inject(RecipeActivity recipeActivity);
}
