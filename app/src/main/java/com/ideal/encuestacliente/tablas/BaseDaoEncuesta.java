package com.ideal.encuestacliente.tablas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.ideal.encuestacliente.model.Esatis_Autopista_Pregunta;
import com.ideal.encuestacliente.model.Esatis_Autopistas;
import com.ideal.encuestacliente.model.Esatis_Catalogo_Preguntas;
import com.ideal.encuestacliente.model.Esatis_Cliente;
import com.ideal.encuestacliente.model.Esatis_Encuesta;
import com.ideal.encuestacliente.model.Esatis_Forma_Pago;
import com.ideal.encuestacliente.model.Esatis_Frecuencia;
import com.ideal.encuestacliente.model.Esatis_Plaza_Cobro;
import com.ideal.encuestacliente.model.Esatis_Ubicaciones;
import com.ideal.encuestacliente.model.Esatis_Usuario;
import com.ideal.encuestacliente.model.Esatis_Valoracion;
import com.ideal.encuestacliente.model.Usuario;
import com.ideal.encuestacliente.sincronizacion.AplicationController;

import java.util.ArrayList;
import java.util.List;

public class BaseDaoEncuesta extends SQLiteOpenHelper {

	private static BaseDaoEncuesta instance;

	private static final int OLD_VERSION = 7;
	private static final int   DATABASE_VERSION = 8;
	public static final String DATABASE_NAME = "EncuestClienteIdealeunodocetrecea.db";

	public static synchronized BaseDaoEncuesta getInstance()
	{
		if(instance == null)
			instance = new BaseDaoEncuesta(AplicationController.getInstance().getBaseContext());
		return instance;
	}

	public BaseDaoEncuesta(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String creaTablaEsatisFormapago = "CREATE TABLE IF NOT EXISTS Esatis_Forma_Pago(" +
				"idFormaPago INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"formaPago TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaEsatisFormapago);

		String creaTablaEsatisCliente = "CREATE TABLE IF NOT EXISTS Esatis_Datos_Cliente(" +
				"idEncuesta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idAutopista INTEGER NOT NULL, " +
				"idPlazaCobro INTEGER NOT NULL, " +
				"fecha VARCHAR(50) NOT NULL, " +
				"encuestador VARCHAR(255) NOT NULL, " +
				"origen VARCHAR(255) NOT NULL, " +
				"destino VARCHAR(255) NOT NULL, " +
				"idFormaPago INTEGER NOT NULL, " +
				"idFrecuenciaUso INTEGER NOT NULL, " +
				"idIndicadorSinc INTEGER NOT NULL, " +
				"observaciones TEXT NOT NULL, " +
                "idUbicacion INTEGER, " +
                "UbicacionDesc TEXT, " +
                "latitud TEXT NOT NULL, " +
                "longitud TEXT NOT NULL, " +
                "altitud TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaEsatisCliente);

		String creaTablaEsatisValoracion = "CREATE TABLE IF NOT EXISTS Esatis_Valoracion(" +
				"idValoracion INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"descValoraciones TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaEsatisValoracion);

		String creaTablaEsatisEncuesta = "CREATE TABLE IF NOT EXISTS Esatis_Encuesta(" +
				"idPregunta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idEncuesta INTEGER NOT NULL, " +
				"idPreguntaCatalogo INTEGER NOT NULL, " +
				"idValoracion INTEGER NOT NULL, " +
				//"observaciones TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaEsatisEncuesta);

		String creaTablaEsatisAutopista = "CREATE TABLE IF NOT EXISTS Rsin_Autopistas(" +
				"idAutopista INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idAutopistaSQL INTEGER NOT NULL, " +
				"nombreAutopista VARCHAR(255) NOT NULL, " +
				"acronimoAutopista VARCHAR(255) NOT NULL, " +
                "idOrden INTEGER NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaEsatisAutopista);

		String creaTablaEsatisPlazaCobro = "CREATE TABLE IF NOT EXISTS Esatis_PlazaCobro(" +
				"idPlazaCobro INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idPlazaCobroSQL INTEGER, " +
				"idAutopista INTEGER NOT NULL, " +
				"plazaCobro TEXT NOT NULL, " +
				"razonSocialAutopista TEXT, " +
				"usuarioCreacion VARCHAR(50) , " +
				"usuarioModificacion VARCHAR(50) , " +
				"usuarioEliminacion VARCHAR(50) , " +
				"fechaCreacion VARCHAR(50) , " +
				"fechaModificacion VARCHAR(50) , " +
				"fechaEliminacion VARCHAR(50) ) ";
		db.execSQL(creaTablaEsatisPlazaCobro);

		String creaTablaCatalogoPreguntas = "CREATE TABLE IF NOT EXISTS Esatis_catalogo_preguntas(" +
				"idPregunta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idPreguntaCatalogo INTEGER NOT NULL, " +
				"descPregunta TEXT NOT NULL, " +
				"descObservaciones TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaCatalogoPreguntas);

		String creaTablaCatalogoAutopistaPregunta = "CREATE TABLE IF NOT EXISTS Esatis_autopista_pregunta(" +
				"idAutopistaPregunta INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"idAutopista INTEGER NOT NULL, " +
				"idPreguntaCatalogo INTEGER NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaCatalogoAutopistaPregunta);

		String creaTablaCatalogoFrecuencia = "CREATE TABLE IF NOT EXISTS Esatis_catalogo_frecuencia(" +
				"idFrecuenciaUso INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"descFrecuencia TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaCatalogoFrecuencia);


		String creaTablaUsuarios = "CREATE TABLE IF NOT EXISTS Usuario(" +
				"idUsuario INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"nombreUsuario INTEGER NOT NULL, " +
				"password TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) , " +
				"usuarioModificacion VARCHAR(50) , " +
				"usuarioEliminacion VARCHAR(50) , " +
				"fechaCreacion VARCHAR(50) , " +
				"fechaModificacion VARCHAR(50) , " +
				"fechaEliminacion VARCHAR(50) ) ";
		db.execSQL(creaTablaUsuarios);

        //db.execSQL("ALTER TABLE Esatis_Datos_Cliente ADD COLUMN idUbicacion INTEGER DEFAULT 0");
        //db.execSQL("ALTER TABLE Esatis_Datos_Cliente ADD COLUMN UbicacionDesc VARCHAR(200)");

		String creaTablaEsatisUbicacion = "CREATE TABLE IF NOT EXISTS Esatis_Ubicaciones(" +
				"idUbicacion INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
				"ubicacion TEXT NOT NULL, " +
				"usuarioCreacion VARCHAR(50) NOT NULL, " +
				"usuarioModificacion VARCHAR(50) NOT NULL, " +
				"usuarioEliminacion VARCHAR(50) NOT NULL, " +
				"fechaCreacion VARCHAR(50) NOT NULL, " +
				"fechaModificacion VARCHAR(50) NOT NULL, " +
				"fechaEliminacion VARCHAR(50) NOT NULL) ";
		db.execSQL(creaTablaEsatisUbicacion);

		String creaTablaCatUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios(" +
				"idUsuario INTEGER NOT NULL, " +
				"nombreUsuario TEXT, " +
				"nombre TEXT, " +
				"idPuesto INTEGER NOT NULL, " +
				"puesto TEXT ) ";
		db.execSQL(creaTablaCatUsuarios);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		if (oldVersion < 3) {

			db.execSQL("ALTER TABLE Esatis_Datos_Cliente ADD COLUMN idUbicacion INTEGER DEFAULT 0");
            db.execSQL("ALTER TABLE Esatis_Datos_Cliente ADD COLUMN UbicacionDesc VARCHAR(200)");

			String creaTablaEsatisValoracion = "CREATE TABLE Esatis_Ubicaciones(" +
					"idUbicacion INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
					"ubicacion TEXT NOT NULL, " +
					"usuarioCreacion VARCHAR(50) NOT NULL, " +
					"usuarioModificacion VARCHAR(50) NOT NULL, " +
					"usuarioEliminacion VARCHAR(50) NOT NULL, " +
					"fechaCreacion VARCHAR(50) NOT NULL, " +
					"fechaModificacion VARCHAR(50) NOT NULL, " +
					"fechaEliminacion VARCHAR(50) NOT NULL) ";
			db.execSQL(creaTablaEsatisValoracion);
		}

		db.execSQL("drop table if exists Esatis_Forma_Pago");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_Datos_Cliente");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_Valoracion");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_Encuesta");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_Autopista");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_PlazaCobro");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_catalogo_preguntas");
		onCreate(db);

		db.execSQL("drop table if exists Esatis_autopista_pregunta");
		onCreate(db);

		db.execSQL("drop table if exists Usuario");
		onCreate(db);

		db.execSQL("drop table if exists Usuarios");
		onCreate(db);
	}



	public boolean insertaAutopistas(Esatis_Autopistas dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosAutopista= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosAutopista.put("idAutopistaSQL", dto.getIdAutopistaSQL());
			datosAutopista.put("nombreAutopista", dto.getNombreAutopista());
			datosAutopista.put("acronimoAutopista", dto.getAcronimoAutopista());
            datosAutopista.put("idOrden", dto.getIdOrden());

			datosAutopista.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosAutopista.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosAutopista.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosAutopista.put("fechaCreacion", dto.getFechaCreacion());
			datosAutopista.put("fechaModificacion", dto.getFechaModificacion());
			datosAutopista.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Rsin_Autopistas",
					"idAutopistaSQL, nombreAutopista, acronimoAutopista, idOrden, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosAutopista);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}


    public boolean insertaPlazaCobro(Esatis_Plaza_Cobro dto){

        boolean estado1=true;
        int resultado1;
        ContentValues datosFormaPago= new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();
        //db.beginTransactionNonExclusive();

        try{
            datosFormaPago.put("idPlazaCobroSQL", dto.getIdPlazaCobroSQL());
			datosFormaPago.put("idAutopista", dto.getIdAutopista());
            datosFormaPago.put("plazaCobro", dto.getPlazaCobro());
			datosFormaPago.put("razonSocialAutopista", dto.getRazonSocialAutopista());
            datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
            datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
            datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
            datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
            datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
            datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

            resultado1=(int)this.getWritableDatabase().insert("Esatis_PlazaCobro",
                    "idPlazaCobroSQL, idAutopista, plazaCobro, razonSocialAutopista, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
                            "fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

            if(resultado1>0)estado1=true;
            else estado1=false;

        }catch(Exception e){
            estado1=false;
        }

        //db.setTransactionSuccessful();
        //db.endTransaction();
        //db.close();

        return estado1;

    }

	public boolean insertUser(Usuario dto){

		boolean estado1;
		int resultado1;
		ContentValues datosUsuarios= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try {
			datosUsuarios.put("idUsuario", dto.getIdUsuario());
			datosUsuarios.put("nombreUsuario", dto.getNombreUsuario());
			datosUsuarios.put("nombre", dto.getNombre());
			datosUsuarios.put("idPuesto", dto.getIdPuesto());
			datosUsuarios.put("puesto", dto.getPuesto());

			resultado1 = (int) this.getWritableDatabase().insert(
					"Usuarios",
					"idUsuario, " +
							"nombreUsuario, " +
							"nombre, " +
							"idPuesto, " +
							"puesto",
					datosUsuarios
			);

			estado1 = resultado1 > 0;

		} catch(Exception e){
			estado1 = false;
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		return estado1;
	}

	public List<Usuario> getUserData(){

		String query = "SELECT * FROM Usuarios";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		c = db.rawQuery(query, null);

		List<Usuario> userList = new ArrayList<>();

		int nombreUsuario, nombre, puesto;

		nombreUsuario = c.getColumnIndex("nombreUsuario");
		nombre = c.getColumnIndex("nombre");
		puesto = c.getColumnIndex("puesto");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Usuario dto = new Usuario();
			dto.setNombreUsuario(c.getString(nombreUsuario));
			dto.setNombre(c.getString(nombre));
			dto.setPuesto(c.getString(puesto));

			userList.add(dto);
		}

		c.close();
		db.close();

		return userList;
	}

	public boolean insertaFormaPago(Esatis_Forma_Pago dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosFormaPago= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosFormaPago.put("formaPago", dto.getFormaPago());
			datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
			datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
			datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_Forma_Pago",
					"formaPago, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}

	public boolean insertaFrecuencia(Esatis_Frecuencia dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosFormaPago= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosFormaPago.put("descFrecuencia", dto.getDescFrecuencia());
			datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
			datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
			datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_catalogo_frecuencia",
					"descFrecuencia, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}

	public boolean insertaValoracion(Esatis_Valoracion dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosFormaPago= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosFormaPago.put("descValoraciones", dto.getDescValoraciones());
			datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
			datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
			datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_Valoracion",
					"descValoraciones, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}


	public boolean insertaUbicacion(Esatis_Ubicaciones dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosFormaPago= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosFormaPago.put("ubicacion", dto.getUbicacion());
			datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
			datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
			datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_Ubicaciones",
					"ubicacion, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}

	public boolean insertaPreguntas(Esatis_Catalogo_Preguntas dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosFormaPago= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosFormaPago.put("idPreguntaCatalogo", dto.getIdPreguntaCatalogo());
			datosFormaPago.put("descPregunta", dto.getDescPregunta());
			datosFormaPago.put("descObservaciones", dto.getDescObservaciones());
			datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
			datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
			datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_catalogo_preguntas",
					"idPreguntaCatalogo, descPregunta, descObservaciones, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}

	public boolean insertaAutopistaPreguntas(Esatis_Autopista_Pregunta dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datosFormaPago= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			datosFormaPago.put("idAutopista", dto.getIdAutopista());
			datosFormaPago.put("idPreguntaCatalogo", dto.getIdPreguntaCatalogo());
			datosFormaPago.put("usuarioCreacion", dto.getUsuarioCreacion());
			datosFormaPago.put("usuarioModificacion", dto.getUsuarioModificacion());
			datosFormaPago.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datosFormaPago.put("fechaCreacion", dto.getFechaCreacion());
			datosFormaPago.put("fechaModificacion", dto.getFechaModificacion());
			datosFormaPago.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_autopista_pregunta",
					"idAutopista, idPreguntaCatalogo, usuarioCreacion, usuarioModificacion, usuarioEliminacion, " +
							"fechaCreacion, fechaModificacion, fechaEliminacion", datosFormaPago);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();

		return estado1;

	}

	public void truncateTable(String tableName){

		SQLiteDatabase db = this.getWritableDatabase();
		//db.beginTransactionNonExclusive();

		try{
			getWritableDatabase().execSQL(
					"DELETE FROM " + tableName
			);
		}catch(Exception e){
			Log.e("truncateTable", e.getMessage());
		}

		//db.setTransactionSuccessful();
		//db.endTransaction();
		//db.close();
	}

	public void resetAutoincrement(String tableName) {

		try{
			getWritableDatabase().execSQL(
					"delete from sqlite_sequence where name='" + tableName + "'"
			);
		}catch(Exception e){
			Log.e("resetAutoincrement", e.getMessage());
		}


		Log.d("DATABASE", tableName + " autoincrement reset");


	}

	public List consultaUsuario(){

		String query = "SELECT * FROM Usuario";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidUsuario,
				InombreUsuario;

		IidUsuario=c.getColumnIndex("idUsuario");
		InombreUsuario=c.getColumnIndex("nombreUsuario");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Usuario dto=new Esatis_Usuario();
			dto.setIdUsuario(c.getInt(IidUsuario));
			dto.setNombreUsuario(c.getString(InombreUsuario));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaAutopistas(){
		String query = "SELECT * FROM Rsin_Autopistas ORDER BY idOrden ASC";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista,
				InombreAutopista,
				IacronimoAutopista,
                IidOrden,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidAutopista=c.getColumnIndex("idAutopistaSQL");
		InombreAutopista=c.getColumnIndex("nombreAutopista");
		IacronimoAutopista=c.getColumnIndex("acronimoAutopista");
        IidOrden=c.getColumnIndex("idOrden");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Autopistas dto=new Esatis_Autopistas();
			dto.setIdAutopistaSQL(c.getInt(IidAutopista));
			dto.setNombreAutopista(c.getString(InombreAutopista));
			dto.setAcronimoAutopista(c.getString(IacronimoAutopista));
            dto.setIdOrden(c.getInt(IidOrden));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaPlazaCobroPorIdAutopita(int idAutop){
		String query = "SELECT * FROM Esatis_PlazaCobro WHERE idAutopista=" + idAutop;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista,
				IidPlazaCobro,
				IidPlazaCobroSQL,
				IplazaCobro,
				IrazonSocial,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidPlazaCobro=c.getColumnIndex("idPlazaCobro");
		IidPlazaCobroSQL=c.getColumnIndex("idPlazaCobroSQL");
		IplazaCobro=c.getColumnIndex("plazaCobro");
		IrazonSocial=c.getColumnIndex("razonSocialAutopista");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Plaza_Cobro dto=new Esatis_Plaza_Cobro();
			dto.setIdPlazaCobro(c.getInt(IidPlazaCobro));
			dto.setIdPlazaCobroSQL(c.getInt(IidPlazaCobroSQL));
			dto.setPlazaCobro(c.getString(IplazaCobro));
			dto.setRazonSocialAutopista(c.getString(IrazonSocial));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaPlazaCobroPorIdPlaza(){
		String query = "SELECT * FROM Esatis_PlazaCobro";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista,
				IidPlazaCobro,
				IidPlazaCobroSQL,
				IplazaCobro,
				IrazonSocial,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidPlazaCobro=c.getColumnIndex("idPlazaCobro");
		IidPlazaCobroSQL=c.getColumnIndex("idPlazaCobroSQL");
		IplazaCobro=c.getColumnIndex("plazaCobro");
		IrazonSocial=c.getColumnIndex("razonSocialAutopista");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Plaza_Cobro dto=new Esatis_Plaza_Cobro();
			dto.setIdPlazaCobro(c.getInt(IidPlazaCobro));
			dto.setIdPlazaCobroSQL(c.getInt(IidPlazaCobroSQL));
			dto.setPlazaCobro(c.getString(IplazaCobro));
			dto.setRazonSocialAutopista(c.getString(IrazonSocial));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}


	public List consultaFormaPago(){
		String query = "SELECT * FROM Esatis_Forma_Pago";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidFormaPago,
				IformaPago,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidFormaPago=c.getColumnIndex("idFormaPago");
		IformaPago=c.getColumnIndex("formaPago");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Forma_Pago dto=new Esatis_Forma_Pago();
			dto.setIdFormaPago(c.getInt(IidFormaPago));
			dto.setFormaPago(c.getString(IformaPago));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaFrecuencia(){
		String query = "SELECT * FROM Esatis_catalogo_frecuencia";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidFrecuencia,
				Ifrecuencia,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidFrecuencia=c.getColumnIndex("idFrecuenciaUso");
		Ifrecuencia=c.getColumnIndex("descFrecuencia");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Frecuencia dto=new Esatis_Frecuencia();
			dto.setIdFrecuenciaUso(c.getInt(IidFrecuencia));
			dto.setDescFrecuencia(c.getString(Ifrecuencia));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}




	public List consultaUbicacion(){
		String query = "SELECT * FROM Esatis_Ubicaciones";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidUbicacion,
				IUbicacion,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidUbicacion=c.getColumnIndex("idUbicacion");
		IUbicacion=c.getColumnIndex("ubicacion");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Ubicaciones dto=new Esatis_Ubicaciones();
			dto.setIdUbicacion(c.getInt(IidUbicacion));
			dto.setUbicacion(c.getString(IUbicacion));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}




	public List consultaValoracion(){
		String query = "SELECT * FROM Esatis_Valoracion";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidValoracion,
				Ivaloracion,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidValoracion=c.getColumnIndex("idValoracion");
		Ivaloracion=c.getColumnIndex("descValoraciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Valoracion dto=new Esatis_Valoracion();
			dto.setIdValoracion(c.getInt(IidValoracion));
			dto.setDescValoraciones(c.getString(Ivaloracion));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaPreguntas(){
		String query = "SELECT * FROM Esatis_catalogo_preguntas";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IdescPregunta,
				IdescObservaciones,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IdescPregunta=c.getColumnIndex("descPregunta");
		IdescObservaciones=c.getColumnIndex("descObservaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Catalogo_Preguntas dto=new Esatis_Catalogo_Preguntas();
			dto.setDescPregunta(c.getString(IdescPregunta));
			dto.setDescObservaciones(c.getString(IdescObservaciones));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaDescPreguntasPorIdAutopista(int idPreguntaCatalgo){

		String query = "SELECT * FROM Esatis_catalogo_preguntas where idPreguntaCatalogo=" + idPreguntaCatalgo;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidPreguntaCatalogo, IdescPregunta,
				IdescObservaciones,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidPreguntaCatalogo=c.getColumnIndex("idPreguntaCatalogo");
		IdescPregunta=c.getColumnIndex("descPregunta");
		IdescObservaciones=c.getColumnIndex("descObservaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Catalogo_Preguntas dto=new Esatis_Catalogo_Preguntas();
			dto.setIdPreguntaCatalogo(c.getInt(IidPreguntaCatalogo));
			dto.setDescPregunta(c.getString(IdescPregunta));
			dto.setDescObservaciones(c.getString(IdescObservaciones));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaPreguntasPorIdAutopista(int idAutopista){
		String query = "SELECT * FROM Esatis_autopista_pregunta WHERE idAutopista=" + idAutopista;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista,
				IidPreguntaCatalogo,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidAutopista=c.getColumnIndex("idAutopista");
		IidPreguntaCatalogo=c.getColumnIndex("idPreguntaCatalogo");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Autopista_Pregunta dto=new Esatis_Autopista_Pregunta();
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setIdPreguntaCatalogo(c.getInt(IidPreguntaCatalogo));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}


	public List consultaPreguntasPorIdAutopistaAll(){
		String query = "SELECT * FROM Esatis_autopista_pregunta";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista,
				IidPreguntaCatalogo,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidAutopista=c.getColumnIndex("idAutopista");
		IidPreguntaCatalogo=c.getColumnIndex("idPreguntaCatalogo");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Autopista_Pregunta dto=new Esatis_Autopista_Pregunta();
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setIdPreguntaCatalogo(c.getInt(IidPreguntaCatalogo));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public boolean insertaDatosCliente(Esatis_Cliente dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datos= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datos.put("idAutopista", dto.getIdAutopista());
			datos.put("idPlazaCobro", dto.getIdPlazaCobro());
			datos.put("idUbicacion", dto.getIdUbicacion());
            datos.put("UbicacionDesc", dto.getUbicacionDesc());
			datos.put("fecha", dto.getFecha());
			datos.put("encuestador", dto.getEncuestador());
			datos.put("origen", dto.getOrigen());
			datos.put("destino", dto.getDestino());
			datos.put("idFormaPago", dto.getIdFormaPago());
			datos.put("idFrecuenciaUso", dto.getIdFrecuenciaUso());
			datos.put("idIndicadorSinc", dto.getIdIndicadorSinc());
			datos.put("observaciones", dto.getObservaciones());
            datos.put("latitud", dto.getLatitud());
            datos.put("longitud", dto.getLongitud());
            datos.put("altitud", dto.getAltitud());
			datos.put("usuarioCreacion", dto.getUsuarioCreacion());
			datos.put("usuarioModificacion", dto.getUsuarioModificacion());
			datos.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datos.put("fechaCreacion", dto.getFechaCreacion());
			datos.put("fechaModificacion", dto.getFechaModificacion());
			datos.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_Datos_Cliente", "idAutopista, " +
					"idPlazaCobro, idUbicacion, UbicacionDesc, fecha, encuestador, origen, destino, idFormaPago, idFrecuenciaUso, idIndicadorSinc, observaciones, latitud, longitud, altitud, " +
                    "usuarioCreacion, " +
					"usuarioModificacion, usuarioEliminacion, fechaCreacion, fechaModificacion, fechaEliminacion", datos);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();


		return estado1;

	}

	public boolean actualizaDatosCliente(Esatis_Cliente dto){
		String[] argumento ={String.valueOf(dto.getIdEncuesta())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("idAutopista", dto.getIdAutopista());
		datos.put("idPlazaCobro", dto.getIdPlazaCobro());
		datos.put("idUbicacion", dto.getIdUbicacion());
        datos.put("UbicacionDesc", dto.getUbicacionDesc());
		datos.put("fecha", dto.getFecha());
		datos.put("encuestador", dto.getEncuestador());
		datos.put("origen", dto.getOrigen());
		datos.put("destino", dto.getDestino());
		datos.put("idFormaPago", dto.getIdFormaPago());
		datos.put("idFrecuenciaUso", dto.getIdFrecuenciaUso());
		datos.put("idIndicadorSinc", dto.getIdIndicadorSinc());
		datos.put("observaciones", dto.getObservaciones());
        datos.put("latitud", dto.getLatitud());
        datos.put("longitud", dto.getLongitud());
        datos.put("altitud", dto.getAltitud());
		datos.put("usuarioCreacion", dto.getUsuarioCreacion());
		datos.put("usuarioModificacion", dto.getUsuarioModificacion());
		datos.put("usuarioEliminacion", dto.getUsuarioEliminacion());
		datos.put("fechaCreacion", dto.getFechaCreacion());
		datos.put("fechaModificacion", dto.getFechaModificacion());
		datos.put("fechaEliminacion", dto.getFechaEliminacion());

		resultado=this.getWritableDatabase().update("Esatis_Datos_Cliente", datos, "idEncuesta=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	public boolean actualizaIndicadorSincronizacion(Esatis_Cliente datos2){
		String[] argumento ={String.valueOf(datos2.getIdEncuesta())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("idIndicadorSinc", datos2.getIdIndicadorSinc());
		datos.put("observaciones", datos2.getObservaciones());

		resultado=this.getWritableDatabase().update("Esatis_Datos_Cliente", datos, "idEncuesta=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}


	public boolean actualizaIndicadorFinalizaSincronizacion(Esatis_Cliente datos2){
		String[] argumento ={String.valueOf(datos2.getIdEncuesta())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("idIndicadorSinc", datos2.getIdIndicadorSinc());

		resultado=this.getWritableDatabase().update("Esatis_Datos_Cliente", datos, "idEncuesta=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	public List consultaIdAutopista(String nombreAutopista){

		String query = "select * from Rsin_Autopistas where nombreAutopista=" + "'" + nombreAutopista + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista;

		IidAutopista=c.getColumnIndex("idAutopistaSQL");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Autopistas dto=new Esatis_Autopistas();
			dto.setIdAutopistaSQL(c.getInt(IidAutopista));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaIdAutopista(int idAutopistas){

		String query = "select * from Rsin_Autopistas where idAutopistaSQL=" +  idAutopistas;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidAutopista, InombreAutopista;

		IidAutopista=c.getColumnIndex("idAutopista");
		InombreAutopista=c.getColumnIndex("nombreAutopista");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Autopistas dto=new Esatis_Autopistas();
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setNombreAutopista(c.getString(InombreAutopista));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;
	}

	public List consultaDatosClientePorIdAutopista(int idAutopita){

		String query = "select * from Esatis_Datos_Cliente where idAutopista=" +  idAutopita;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidContadorEncuesta, IidEncuesta, IidAutopista, IidPlazaCobro, Ifecha, Iencuestador, Iorigen, Idestino, IidFormaPago, IidFrecuenciaUso,
		IindicadorSinc, Iobservaciones, IusuarioCreacion, IusuarioModificacion, IusuarioEliminacion, IfechaCreacion, IfechaModificacion, IfechaEliminacion;

		IidEncuesta=c.getColumnIndex("idEncuesta");
		IidAutopista=c.getColumnIndex("idAutopista");
		IidPlazaCobro=c.getColumnIndex("idPlazaCobro");
		Ifecha=c.getColumnIndex("fecha");
		Iencuestador=c.getColumnIndex("encuestador");
		Iorigen=c.getColumnIndex("origen");
		Idestino=c.getColumnIndex("destino");
		IidFormaPago=c.getColumnIndex("idFormaPago");
		IidFrecuenciaUso=c.getColumnIndex("idFrecuenciaUso");
		IindicadorSinc=c.getColumnIndex("idIndicadorSinc");
		Iobservaciones=c.getColumnIndex("observaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Cliente dto=new Esatis_Cliente();
			dto.setIdEncuesta(c.getInt(IidEncuesta));
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setIdPlazaCobro(c.getInt(IidPlazaCobro));
			dto.setFecha(c.getString(Ifecha));
			dto.setEncuestador(c.getString(Iencuestador));
			dto.setOrigen(c.getString(Iorigen));
			dto.setDestino(c.getString(Idestino));
			dto.setIdFormaPago(c.getInt(IidFormaPago));
			dto.setIdFrecuenciaUso(c.getInt(IidFrecuenciaUso));
			dto.setIdIndicadorSinc(c.getInt(IindicadorSinc));
			dto.setObservaciones(c.getString(Iobservaciones));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}


	public List consultaDatosClientePorIdEncuesta(int idEncuesta){

		String query = "select * from Esatis_Datos_Cliente where idEncuesta=" +  idEncuesta;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidEncuesta, IidAutopista, IidPlazaCobro, IidUbicacion, IUbicacion, Ifecha, Iencuestador, Iorigen, Idestino, IidFormaPago, IidFrecuenciaUso,
				Iobservaciones, Ilatitud, Ilongitud, Ialtitud, IusuarioCreacion, IusuarioModificacion, IusuarioEliminacion, IfechaCreacion, IfechaModificacion, IfechaEliminacion;

		IidEncuesta=c.getColumnIndex("idEncuesta");
		IidAutopista=c.getColumnIndex("idAutopista");
		IidPlazaCobro=c.getColumnIndex("idPlazaCobro");
		IidUbicacion=c.getColumnIndex("idUbicacion");
        IUbicacion=c.getColumnIndex("UbicacionDesc");
		Ifecha=c.getColumnIndex("fecha");
		Iencuestador=c.getColumnIndex("encuestador");
		Iorigen=c.getColumnIndex("origen");
		Idestino=c.getColumnIndex("destino");
		IidFormaPago=c.getColumnIndex("idFormaPago");
		IidFrecuenciaUso=c.getColumnIndex("idFrecuenciaUso");
		Iobservaciones=c.getColumnIndex("observaciones");
        Ilatitud=c.getColumnIndex("latitud");
        Ilongitud=c.getColumnIndex("longitud");
        Ialtitud=c.getColumnIndex("altitud");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Cliente dto=new Esatis_Cliente();
			dto.setIdEncuesta(c.getInt(IidEncuesta));
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setIdPlazaCobro(c.getInt(IidPlazaCobro));
			dto.setIdUbicacion(c.getInt(IidUbicacion));
			dto.setUbicacionDesc(c.getString(IUbicacion));
			dto.setFecha(c.getString(Ifecha));
			dto.setEncuestador(c.getString(Iencuestador));
			dto.setOrigen(c.getString(Iorigen));
			dto.setDestino(c.getString(Idestino));
			dto.setIdFormaPago(c.getInt(IidFormaPago));
			dto.setIdFrecuenciaUso(c.getInt(IidFrecuenciaUso));
			dto.setObservaciones(c.getString(Iobservaciones));
            dto.setAltitud(c.getDouble(Ialtitud));
            dto.setLongitud(c.getDouble(Ilongitud));
            dto.setLatitud(c.getDouble(Ilatitud));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List consultaUltimoDatosCliente(int idAutop){

		String query = "select * from Esatis_Datos_Cliente WHERE idAutopista = "+ idAutop
				+ " ORDER BY idEncuesta DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidEncuesta, IidAutopista, IidPlazaCobro, Ifecha, Iencuestador, Iorigen, Idestino, IidFormaPago, IidFrecuenciaUso,
				Iobservaciones, IusuarioCreacion, IusuarioModificacion, IusuarioEliminacion, IfechaCreacion, IfechaModificacion, IfechaEliminacion;

		IidEncuesta=c.getColumnIndex("idEncuesta");
		IidAutopista=c.getColumnIndex("idAutopista");
		IidPlazaCobro=c.getColumnIndex("idPlazaCobro");
		Ifecha=c.getColumnIndex("fecha");
		Iencuestador=c.getColumnIndex("encuestador");
		Iorigen=c.getColumnIndex("origen");
		Idestino=c.getColumnIndex("destino");
		IidFormaPago=c.getColumnIndex("idFormaPago");
		IidFrecuenciaUso=c.getColumnIndex("idFrecuenciaUso");
		Iobservaciones=c.getColumnIndex("observaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Cliente dto=new Esatis_Cliente();
			dto.setIdEncuesta(c.getInt(IidEncuesta));
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setIdPlazaCobro(c.getInt(IidPlazaCobro));
			dto.setFecha(c.getString(Ifecha));
			dto.setEncuestador(c.getString(Iencuestador));
			dto.setOrigen(c.getString(Iorigen));
			dto.setDestino(c.getString(Idestino));
			dto.setIdFormaPago(c.getInt(IidFormaPago));
			dto.setIdFrecuenciaUso(c.getInt(IidFrecuenciaUso));
			dto.setObservaciones(c.getString(Iobservaciones));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List consultaPreguntasClientePorIdEncuesta(int idEncuesta){

		String query = "select * from Esatis_Encuesta where idEncuesta=" +  idEncuesta;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int 	IidPregunta,
				IidEncuesta,
				IidPreguntaCatalogo,
				IidValoracion,
				Iobservaciones,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidPregunta=c.getColumnIndex("idPregunta");
		IidEncuesta=c.getColumnIndex("idEncuesta");
		IidPreguntaCatalogo=c.getColumnIndex("idPreguntaCatalogo");
		IidValoracion=c.getColumnIndex("idValoracion");
		//Iobservaciones=c.getColumnIndex("observaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Encuesta dto=new Esatis_Encuesta();
			dto.setIdPregunta(c.getInt(IidPregunta));
			dto.setIdEncuesta(c.getInt(IidEncuesta));
			dto.setIdPreguntaCatalogo(c.getInt(IidPreguntaCatalogo));
			dto.setIdValoracion(c.getInt(IidValoracion));
			//dto.setObservaciones(c.getString(Iobservaciones));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}


	public int regresaIDAutopista(String nombreAutopista){

		String query = "SELECT idAutopistaSQL "
				+ " FROM Rsin_Autopistas"
				+ " WHERE nombreAutopista " + " = " +  "'" + nombreAutopista + "'" ;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);
		int descripcion = 0;

		int IidAutopista;
		IidAutopista = c.getColumnIndex("idAutopistaSQL");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			descripcion = c.getInt(IidAutopista);
		}

		c.close();
		db.close();

		return descripcion;
	}

	public List consultaPreguntasCliente(){

		String query = "select * from Esatis_Encuesta ORDER BY idPregunta DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int 	IidPregunta,
				IidEncuesta,
				IidPreguntaCatalogo,
				IidValoracion,
				Iobservaciones,
				IusuarioCreacion,
				IusuarioModificacion,
				IusuarioEliminacion,
				IfechaCreacion,
				IfechaModificacion,
				IfechaEliminacion;

		IidPregunta=c.getColumnIndex("idPregunta");
		IidEncuesta=c.getColumnIndex("idEncuesta");
		IidPreguntaCatalogo=c.getColumnIndex("idPreguntaCatalogo");
		IidValoracion=c.getColumnIndex("idValoracion");
		//Iobservaciones=c.getColumnIndex("observaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Encuesta dto=new Esatis_Encuesta();
			dto.setIdPregunta(c.getInt(IidPregunta));
			dto.setIdEncuesta(c.getInt(IidEncuesta));
			dto.setIdPreguntaCatalogo(c.getInt(IidPreguntaCatalogo));
			dto.setIdValoracion(c.getInt(IidValoracion));
			//dto.setObservaciones(c.getString(Iobservaciones));
			dto.setUsuarioCreacion(c.getString(IusuarioCreacion));
			dto.setUsuarioModificacion(c.getString(IusuarioModificacion));
			dto.setUsuarioEliminacion(c.getString(IusuarioEliminacion));
			dto.setFechaCreacion(c.getString(IfechaCreacion));
			dto.setFechaModificacion(c.getString(IfechaModificacion));
			dto.setFechaEliminacion(c.getString(IfechaEliminacion));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public List consultaDatosCliente(){

		String query = "select * from Esatis_Datos_Cliente ORDER BY idEncuesta DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = null;
		c= db.rawQuery(query, null);

		List regresamos1= new ArrayList();

		int IidEncuesta, IidAutopista, IidPlazaCobro, Ifecha, Iencuestador, Iorigen, Idestino, IidFormaPago, IidFrecuenciaUso,
				Iobservaciones, IusuarioCreacion, IusuarioModificacion, IusuarioEliminacion, IfechaCreacion, IfechaModificacion, IfechaEliminacion;

		IidEncuesta=c.getColumnIndex("idEncuesta");
		IidAutopista=c.getColumnIndex("idAutopista");
		IidPlazaCobro=c.getColumnIndex("idPlazaCobro");
		Ifecha=c.getColumnIndex("fecha");
		Iencuestador=c.getColumnIndex("encuestador");
		Iorigen=c.getColumnIndex("origen");
		Idestino=c.getColumnIndex("destino");
		IidFormaPago=c.getColumnIndex("idFormaPago");
		IidFrecuenciaUso=c.getColumnIndex("idFrecuenciaUso");
		Iobservaciones=c.getColumnIndex("observaciones");
		IusuarioCreacion=c.getColumnIndex("usuarioCreacion");
		IusuarioModificacion=c.getColumnIndex("usuarioModificacion");
		IusuarioEliminacion=c.getColumnIndex("usuarioEliminacion");
		IfechaCreacion=c.getColumnIndex("fechaCreacion");
		IfechaModificacion=c.getColumnIndex("fechaModificacion");
		IfechaEliminacion=c.getColumnIndex("fechaEliminacion");

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){

			Esatis_Cliente dto=new Esatis_Cliente();
			dto.setIdEncuesta(c.getInt(IidEncuesta));
			dto.setIdAutopista(c.getInt(IidAutopista));
			dto.setIdPlazaCobro(c.getInt(IidPlazaCobro));
			dto.setFecha(c.getString(Ifecha));
			dto.setEncuestador(c.getString(Iencuestador));
			dto.setOrigen(c.getString(Iorigen));
			dto.setDestino(c.getString(Idestino));
			dto.setIdFormaPago(c.getInt(IidFormaPago));
			dto.setIdFrecuenciaUso(c.getInt(IidFrecuenciaUso));
			dto.setObservaciones(c.getString(Iobservaciones));

			regresamos1.add(dto);
		}

		c.close();
		db.close();

		return regresamos1;


	}

	public boolean insertaPreguntas(Esatis_Encuesta dto){

		boolean estado1=true;
		int resultado1;
		ContentValues datos= new ContentValues();

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		try{
			datos.put("idEncuesta", dto.getIdEncuesta());
			datos.put("idPreguntaCatalogo", dto.getIdPreguntaCatalogo());
			datos.put("idValoracion", dto.getIdValoracion());
			//datos.put("observaciones", dto.getObservaciones());
			datos.put("usuarioCreacion", dto.getUsuarioCreacion());
			datos.put("usuarioModificacion", dto.getUsuarioModificacion());
			datos.put("usuarioEliminacion", dto.getUsuarioEliminacion());
			datos.put("fechaCreacion", dto.getFechaCreacion());
			datos.put("fechaModificacion", dto.getFechaModificacion());
			datos.put("fechaEliminacion", dto.getFechaEliminacion());

			resultado1=(int)this.getWritableDatabase().insert("Esatis_Encuesta", "idEncuesta, " +
					"idPreguntaCatalogo, idValoracion, usuarioCreacion, " +
					"usuarioModificacion, usuarioEliminacion, fechaCreacion, fechaModificacion, fechaEliminacion", datos);

			if(resultado1>0)estado1=true;
			else estado1=false;

		}catch(Exception e){
			estado1=false;
		}

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();


		return estado1;

	}

	public boolean actualizaPreguntas(Esatis_Encuesta datos2){
		String[] argumento ={String.valueOf(datos2.getIdPregunta())};

		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("idEncuesta", datos2.getIdEncuesta());
		datos.put("idPreguntaCatalogo", datos2.getIdPreguntaCatalogo());
		datos.put("idValoracion", datos2.getIdValoracion());
		//datos.put("observaciones", datos2.getObservaciones());
		datos.put("usuarioCreacion", datos2.getUsuarioCreacion());
		datos.put("usuarioModificacion", datos2.getUsuarioModificacion());
		datos.put("usuarioEliminacion", datos2.getUsuarioEliminacion());
		datos.put("fechaCreacion", datos2.getFechaCreacion());
		datos.put("fechaModificacion", datos2.getFechaModificacion());
		datos.put("fechaEliminacion", datos2.getFechaEliminacion());

		resultado=this.getWritableDatabase().update("Esatis_Encuesta", datos, "idPregunta=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}


	public boolean actualizaClaveSqlSincronizacionDatosCliente(Esatis_Cliente datos2){
		String[] argumento ={String.valueOf(datos2.getIdEncuesta())};
		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("fechaModificacion", datos2.getFechaModificacion());

		resultado=this.getWritableDatabase().update("Esatis_Datos_Cliente", datos, "idEncuesta=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}

	public boolean actualizaClaveSqlSincronizacionPreguntas(Esatis_Encuesta datos2){
		String[] argumento ={String.valueOf(datos2.getIdPregunta())};

		ContentValues datos=new ContentValues();
		int resultado;

		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransactionNonExclusive();

		datos.put("fechaModificacion", datos2.getFechaModificacion());

		resultado=this.getWritableDatabase().update("Esatis_Encuesta", datos, "idPregunta=?", argumento);

		db.setTransactionSuccessful();
		db.endTransaction();
		db.close();

		if (resultado>0)return true;
		else return false;

	}



}
