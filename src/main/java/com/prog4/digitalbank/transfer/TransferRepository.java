package com.prog4.digitalbank.transfer;

import com.prog4.digitalbank.CrudOperations.FindAll;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TransferRepository {
    private Connection connection;
    private FindAll<Transfer> useConvertToList;
    public void updateTransferStatus(String transferId, String status) throws SQLException {
        String sql = "update transaction set status = ? where transfer_id = ? ";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,status);
            statement.setString(2,transferId);
            statement.executeUpdate();
        }
    }

    public List<Transfer> appendingTransfer(){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "select transfer.* from transfer inner join transaction \n" +
                "on transfer.id = transaction.transfer_id\n" +
                " where transaction.status = 'apending' \n" +
                "and transfer.effective_date <= current_timestamp \n" +
                "and transaction.type = 'debit'";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
            transfers.add(useConvertToList.convertToList(resultSet , Transfer.class));
            }
            return transfers;
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
