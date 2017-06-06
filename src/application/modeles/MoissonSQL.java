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

public class MoissonSQL {

    public static void addMoisson(Commande inputCommande, Vehicule inputVehicule, String intputJ_debut, String inputH_debut, String inputJ_fin, String inputH_fin, Float inputNbKilo, Float inputNbTonne) {
        String request = "UPDATE Ordre SET tonnes=:tonnes, nb_km_ordre=:nbKilo, duree_ordre:duree, heure_arrive_ordre:heureArrive WHERE id_vehi=:vehi AND id_com=:com";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("tonnes", "" + inputNbTonne);
            preparedStatement.setString("nbKilo", "" + inputNbKilo );
            preparedStatement.setString("heureArrive", inputJ_fin + " * " +inputH_fin );
            preparedStatement.setFloat("id_vehi", inputVehicule.getId());
            preparedStatement.setInt("com", inputCommande.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void editCommande(Commande commande) {
            String request = "UPDATE Commande SET date_com=:date_com, bott_com=:bott_com, transp_com=:transp_com, taille_max_transp_com=:taille_max_transp_com, id_champ=:id_champ WHERE id_com=:id_com";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("date_com", commande.getDate().toString());
            preparedStatement.setString("bott_com", commande.getTypebott());
            preparedStatement.setString("transp_com", commande.getTransport());
            preparedStatement.setFloat("taille_max_transp_com", commande.getTaillemax());
            preparedStatement.setInt("id_champ", commande.getChampCommande().getId());
            preparedStatement.setInt("id_com", commande.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static ObservableList<Commande> getCommandeList(int max_entries) {

        String request = "SELECT * FROM Commande INNER JOIN Champ ON Champ.id_champ=Commande.id_champ INNER JOIN Agriculteur ON Agriculteur.id_agri=Champ.id_agri INNER JOIN Culture ON Culture.id_cul=Champ.type_champ";
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
                                rs.getString("type_cul"),
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
