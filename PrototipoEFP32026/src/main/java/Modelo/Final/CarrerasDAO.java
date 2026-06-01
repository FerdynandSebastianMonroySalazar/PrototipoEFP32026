package Modelo.Final; // Paquete donde está el DAO

import Modelo.Conexion; // Clase para obtener conexión a la BD
import Controlador.Final.clsCarreras; // Clase modelo (objeto transportista)
import Controlador.clsUsuarioConectado; // Usuario logueado
import Modelo.BitacoraDAO; // DAO para bitácora
import java.sql.*; // Librerías SQL
import java.util.*; // Listas

public class CarrerasDAO {

    // ========================= INSERTAR =========================
    
    public boolean insertar(clsCarreras obj) {

            String sql = "INSERT INTO carreras (nombre_carrera, codigo_facultad, estatus_carrera) VALUES (?, ?, ?)";

    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, obj.getNombreCarrera());
        ps.setString(2, obj.getCodigoFacultad());
        ps.setString(3, obj.getEstatusCarrera());

        return ps.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
    }

    // ========================= ACTUALIZAR =========================
    public boolean actualizar(clsCarreras obj) {

        String sql = "UPDATE carreras "
                   + "SET nombre_carrera=?, codigo_facultad=?, estatus_carrera=? "
                   + "WHERE codigo_carrera=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, obj.getNombreCarrera());
            ps.setString(2, obj.getCodigoFacultad());
            ps.setString(3, obj.getEstatusCarrera());
            ps.setInt(4, obj.getCodigoCarrera());

            boolean exito = ps.executeUpdate() > 0;

            if (exito) {
                registrarBitacora("Actualizó carrera " + obj.getCodigoCarrera());
            }

            return exito;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================= ELIMINAR =========================
    public boolean eliminar(String codigo) {

        String sql = "DELETE FROM carreras WHERE codigo_carrera=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            boolean exito = ps.executeUpdate() > 0;

            if (exito) {
                registrarBitacora("Eliminó carrera " + codigo);
            }

            return exito;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================= LISTAR =========================
    public List<clsCarreras> listar() {

        List<clsCarreras> lista = new ArrayList<>();

        String sql = "SELECT * FROM carreras";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsCarreras obj = new clsCarreras();

                obj.setCodigoCarrera(rs.getInt("codigo_carrera"));
                obj.setNombreCarrera(rs.getString("nombre_carrera"));
                obj.setCodigoFacultad(rs.getString("codigo_facultad"));
                obj.setEstatusCarrera(rs.getString("estatus_carrera"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ========================= BUSCAR POR ID =========================
    public clsCarreras buscarPorCodigo(String codigo) {

        String sql = "SELECT * FROM carreras WHERE codigo_carrera=?";

        clsCarreras obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsCarreras();

                //obj.setCodigoCarrera(rs.getString("codigo_carrera"));
                obj.setNombreCarrera(rs.getString("nombre_carrera"));
                obj.setCodigoFacultad(rs.getString("codigo_facultad"));
                obj.setEstatusCarrera(rs.getString("estatus_carrera"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    // ========================= BITACORA =========================
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId();

        if (usuario == 0) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        BitacoraDAO bitacora = new BitacoraDAO();
        int aplCodigoBitacora = 2000;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }

}