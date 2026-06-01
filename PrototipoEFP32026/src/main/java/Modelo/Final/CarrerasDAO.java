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

        // QUERY SQL → aquí defines qué campos insertas
        // 🔴 SI AGREGAS UN CAMPO NUEVO EN LA TABLA → debes agregarlo aquí
        String sql = "INSERT INTO transportistas "
                   + "(Trantipovehiculo, Empcodigo) "
                   + "VALUES (?, ?)";

        try (Connection con = Conexion.getConnection(); // Abre conexión
             PreparedStatement ps = con.prepareStatement(sql)) { // Prepara query

            // Aquí asignas valores a los "?"
            // 🔴 SI AGREGAS CAMPO → agregas otro ps.setXXX()
            ps.setString(1, obj.getTrantipovehiculo());
            ps.setInt(2, obj.getEmpcodigo());

            boolean exito = ps.executeUpdate() > 0; // Ejecuta INSERT

            // BITACORA (registro de acciones)
            if (exito) {
                try {
                    registrarBitacora(
                        "Insertó transportista ID " + obj.getEmpcodigo()
                    );
                } catch (Exception ex) {
                    System.out.println("Error bitácora INSERT: " + ex.getMessage());
                }
            }

            return exito;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================= ACTUALIZAR =========================
    public boolean actualizar(clsCarreras obj) {

        // 🔴 SI AGREGAS CAMPO → agregarlo en el SET
        String sql = "UPDATE transportistas "
                   + "SET Trantipovehiculo=?, Empcodigo=? "
                   + "WHERE Tranid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // 🔴 MISMO ORDEN que en el SQL
            ps.setString(1, obj.getTrantipovehiculo());
            ps.setInt(2, obj.getEmpcodigo());
            ps.setInt(3, obj.getTranid());

            boolean exito = ps.executeUpdate() > 0;

            if (exito) {
                try {
                    registrarBitacora(
                        "Actualizó transportista ID " + obj.getTranid()
                    );
                } catch (Exception ex) {
                    System.out.println("Error bitácora UPDATE: " + ex.getMessage());
                }
            }

            return exito;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ========================= ELIMINAR =========================
    public boolean eliminar(int id) {

        // Elimina por ID
        String sql = "DELETE FROM transportistas WHERE Tranid=?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            boolean exito = ps.executeUpdate() > 0;

            if (exito) {
                try {
                    registrarBitacora("Eliminó transportista ID " + id);
                } catch (Exception ex) {
                    System.out.println("Error bitácora DELETE: " + ex.getMessage());
                }
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

        String sql = "SELECT * FROM transportistas"; // Trae todo

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                clsCarreras obj = new clsCarreras();

                // 🔴 SI AGREGAS CAMPO → debes leerlo aquí
                obj.setTranid(rs.getInt("Tranid"));
                obj.setTrantipovehiculo(rs.getString("Trantipovehiculo"));
                obj.setEmpcodigo(rs.getInt("Empcodigo"));

                lista.add(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ========================= BUSCAR POR ID =========================
    public clsCarreras buscarPorId(int id) {

        String sql = "SELECT * FROM transportistas WHERE Tranid=?";

        clsCarreras obj = null;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                obj = new clsCarreras();

                // 🔴 MISMO: si agregas campo → leer aquí
                obj.setTranid(rs.getInt("Tranid"));
                obj.setTrantipovehiculo(rs.getString("Trantipovehiculo"));
                obj.setEmpcodigo(rs.getInt("Empcodigo"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    // ========================= BITACORA =========================
    private void registrarBitacora(String accion) {

        int usuario = clsUsuarioConectado.getUsuId(); // Obtiene usuario logueado

        if (usuario == 0) {
            throw new RuntimeException("No hay usuario autenticado");
        }

        BitacoraDAO bitacora = new BitacoraDAO();

        int aplCodigoBitacora = 2000;

        bitacora.insert(usuario, aplCodigoBitacora, accion);
    }
}