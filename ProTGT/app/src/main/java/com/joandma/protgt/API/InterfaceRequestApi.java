package com.joandma.protgt.API;

import com.joandma.protgt.Models.Aviso;
import com.joandma.protgt.Models.Direccion;
import com.joandma.protgt.Models.User;

import java.security.PublicKey;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Jorge Amores on 25/02/2018.
 */

public interface InterfaceRequestApi {

    //Registro de usuario
    @POST("protgt/api/v1/auth/register")
    public Call<User> registerUser(@Body User newUser);

    //Verificar si ya existe el email o teléfono introducido por el usuario en la base de datos
    //TODO Duda en la respuesta Call<User>
    @POST("protgt/api/v1/auth/register/verify")
    public Call<User> verifyEmailTelephone(@Body String emailOrTelephone);

    //Login de usuario
    @POST("/protgt/api/v1/auth/login")
    public Call<User> loginUser(@Body User userLoged);

    //Añadir una dirección
    @POST("protgt/api/v1/address/addaddress")
    public Call<Direccion> addDireccion(@Body Direccion newDireccion);

    //Añadir un aviso
    @POST("protgt/api/v1/aviso/addaviso")
    public Call<Aviso> addAviso(@Header("Authorization") String token, @Body Aviso newAviso);

    //Obtener detalles de un aviso
    @GET ("/protgt/api/v1/aviso/{id_aviso}")
    public Call<Aviso> showDetailAviso(@Header("Authorization") String token, @Path("id_aviso") String id_aviso);

    //Obtener direcciones de un usuario
    @GET("protgt/api/v1/auth/showaddresses")
    public Call<List<Direccion>> showAddressesOfUser(@Header("Authorization") String token);

    //Obtener todos los avisos
    @GET("protgt/api/v1/aviso/")
    public Call<List<Aviso>> showAllAvisos(@Header("Authorization") String token);

    //Obtener avisos sin resolver (Estado = 0)
    @GET("protgt/api/v1/aviso/sinresolver")
    public Call<List<Aviso>> showAvisosSinResolver(@Header("Authorization") String token);

    //Cambiar estado de un aviso
    @PUT("protgt/api/v1/aviso/{id_aviso}/changestatus")
    public Call<Aviso> changeStatusOfAviso(@Header("Authorization") String token, @Path("id_aviso") String id_aviso, @Body String estado);

    //Editar datos de usuario
    @PUT("protgt/api/v1/auth/edit")
    public Call<User> editUser(@Header("Authorization") String token, @Body User user);

    //Editar una dirección del usuario
    @PUT("protgt/api/v1/auth/edit/address")
    public Call<Direccion> editAddressOfUser(@Header("Authorization") String token, @Body Direccion direccion);

    //Añadir una dirección al usuario
    @PUT("protgt/api/v1/auth/addaddress")
    public Call<Direccion> addAddressToUser(@Header("Authorization") String token, @Body Direccion direccion);

    //Enviar localización de aviso
    @PUT("protgt/api/v1/aviso/{id_aviso}/sendlocation")
    public Call<Aviso> sendLocation(@Header("Authorization") String token, @Body String localizacion);

    //Eliminar una dirección del usuario
    @DELETE("protgt/api/v1/auth/deleteaddress")
    public Call<Direccion> deleteAddressOfUser(@Header("Authorization") String token, @Body String id_direccion);
}