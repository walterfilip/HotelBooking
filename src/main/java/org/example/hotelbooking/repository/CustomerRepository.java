//package org.example.hotelbooking.repository;
//import DatabaseConnection.java;
//
//import java.sql.Connection;
//
//public class CustomerRepository {
//
//    public String getListOfAllNoteID() {
//        String sql = "SELECT id FROM notes";
//
//        try (Connection connection = DatabaseConnection.getConnection();
//             PreparedStatement statement = connection.prepareStatement(sql)) {
//
//            ResultSet resultSet = statement.executeQuery();
//            int noteID;
//            try {
//                while (resultSet.next()) {
//                    noteID = resultSet.getInt("id");
//
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//}
