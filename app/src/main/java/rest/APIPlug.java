package rest;

import models.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIPlug
{
    @GET("films")
    Call<FilmQr> searchFilms(@Query("search") String search);

    @GET("people")
    Call<PeopleQr> searchPeople(@Query("search") String search);

    @GET("people/{id}")
    Call<PeopleQr> searchRandomPeople(@Path("id") String search);

    @GET("planets")
    Call<PlanetQr> searchPlanets(@Query("search") String search);
}
