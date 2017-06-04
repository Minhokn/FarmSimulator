package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandeSQL {

    public static void addCommande(String inputDate, String inputTypeBott, String inputTransport, float inputTailleMax, Champ inputChamp) {
        String request = "INSERT INTO Commande(date_com, bott_com, transp_com, taille_max_transp_com, id_champ) VALUES(:date, :bott, :transp, :tMaxTransp, :champ)";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("date", inputDate);
            preparedStatement.setString("bott", inputTypeBott);
            preparedStatement.setString("transp", inputTransport);
            preparedStatement.setFloat("tMaxTransp", inputTailleMax);
            preparedStatement.setInt("champ", inputChamp.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static ObservableList<Commande> getCommandeList(int max_entries) {

        String request = "SELECT * FROM Commande INNER JOIN Champ ON Champ.id_champ=Commande.id_champ INNER JOIN Agriculteur ON Agriculteur.id_agri=Champ.id_agri";
        if(max_entries > 0) {
            request += " ORDER BY date_com LIMIT " + max_entries;
        }

        ObservableList<Commande> commandeList = FXCollections.observableArrayList();

        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));
                Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));

                commandeList.add(new Commande(
                        Integer.parseInt(rs.getString("id_com")),
                        rs.getString("transp_com"),
                        rs.getString("bott_com"),
                        Float.parseFloat(rs.getString("taille_max_transp_com")),
                        rs.getString("date_com"),
                        Float.parseFloat(rs.getString("tonne_com")),
                        Float.parseFloat(rs.getString("cout_com")),
                        new Champ(
                                Integer.parseInt(rs.getString("id_agri")),
                                Float.parseFloat(rs.getString("surf_champ")),
                                rs.getString("adr_champ"),
                                coord_center,
                                coord_champ,
                                rs.getString("type_champ"),
                                new Agriculteur(
                                            Integer.parseInt(rs.getString("id_agri")),
                                            rs.getString("prenom_agri"),
                                            rs.getString("nom_agri"),
                                            rs.getString("tel_agri"),
                                            rs.getString("adr_agri"),
                                            rs.getString("email_agri")
                                )
                        )
               ));
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return commandeList;
    }

    public static ObservableList<Commande> getCommandeList() {
        return getCommandeList(0);
    }

    public static void deleteCommande(Commande commande) {
        String request = "DELETE FROM Commande WHERE id_com=:id";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            preparedStatement.setInt("id", commande.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
