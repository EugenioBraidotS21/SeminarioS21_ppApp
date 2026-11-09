package com.pandapickapp.util;
	
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class IniciaBase {
    public static void inicializarBaseDeDatos(String urlDB) {
        String sql = """
				CREATE TABLE IF NOT EXISTS rubros (
				    id INTEGER PRIMARY KEY AUTOINCREMENT,
				    nombre TEXT NOT NULL UNIQUE
				);
				
				
				CREATE TABLE IF NOT EXISTS productos (
				    id INTEGER PRIMARY KEY AUTOINCREMENT,
				    nombre TEXT NOT NULL,
				    precio REAL NOT NULL DEFAULT 0 CHECK (precio >= 0),
				    cantidad INTEGER NOT NULL DEFAULT 0,
				    rubro_id INTEGER NOT NULL,
				    UNIQUE (nombre, rubro_id),
				    FOREIGN KEY (rubro_id) REFERENCES rubros(id) ON DELETE CASCADE
				);
				
				
				INSERT OR IGNORE INTO rubros (id, nombre) VALUES (1, 'Almacén y Kiosco');
				INSERT OR IGNORE INTO rubros (id, nombre) VALUES (2, 'Bebidas');
				INSERT OR IGNORE INTO rubros (id, nombre) VALUES (3, 'Limpieza y Perfumería');
				INSERT OR IGNORE INTO rubros (id, nombre) VALUES (4, 'Lácteos y Fiambrería');
				
				
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Yerba Mate 1kg', 6500, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Fideos Tallarín 500g', 1800, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Arroz Largo Fino 1kg', 2200, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Aceite de Girasol 900ml', 3200, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Harina de Trigo 000 1kg', 1500, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Azúcar Común 1kg', 1700, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Puré de Tomate 520g', 1300, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Galletitas de Agua 3 Paquetes', 2400, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Café Instantáneo 170g', 7800, 0, 1);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Atún en Aceite 170g', 2900, 0, 1);
				
				
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Agua Mineral sin Gas 1.5L', 1400, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Gaseosa Cola 2.25L', 3300, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Gaseosa Lima-Limón 2.25L', 3200, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Jugo Naranja 1L', 2100, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Cerveza Rubia 1L', 2800, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Vino Malbec 750ml', 5500, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Agua Saborizada Pomelo 1.5L', 1700, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Bebida Energética 473ml', 2600, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Sidra 720ml', 3000, 0, 2);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Soda En Sifón 1.5L', 1200, 0, 2);
				
				
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Lavandina Tradicional 1L', 1100, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Detergente Líquido 500ml', 2000, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Jabón en Polvo 800g', 4200, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Suavizante para Ropa 900ml', 3100, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Papel Higiénico 4 Unidades', 3500, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Rollo de Cocina 3 Unidades', 3300, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Desinfectante de Pisos 1L', 1900, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Esponja Multiuso Doble Faz', 900, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Jabón de Tocador 3 Unidades', 2700, 0, 3);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Alcohol en Gel 250ml', 2300, 0, 3);
				
				
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Leche Entera Sachet 1L', 1600, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Queso Tybo feteado 100g', 2800, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Jamón Cocido feteado 100g', 2600, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Manteca 200g', 3400, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Yogur Firme de Frutilla 190g', 1000, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Queso Crema 300g', 3000, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Dulce de Leche Clásico 400g', 3600, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Crema de Leche 200ml', 1800, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Salame Milán en Rodajas 100g', 3200, 0, 4);
				INSERT OR IGNORE INTO productos (nombre, precio, cantidad, rubro_id) VALUES ('Queso Untable Light 200g', 2400, 0, 4);
			    """;

	        try (Connection conn = DriverManager.getConnection(urlDB);
	             Statement stmt = conn.createStatement()) {
	            stmt.executeUpdate(sql);
	            System.out.println("Tablas 'rubros' y 'productos' creadas o verificadas exitosamente.");
	        } catch (Exception e) {
	            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
	        }
    }
}