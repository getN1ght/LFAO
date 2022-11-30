package com.example.lfao.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.lfao.data.model.LoggedInUser;

import java.util.List;
import java.util.Objects;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private final LoginDao loginDao;
    private final LiveData<List<LoginTable>> allData;



    private LoginRepository(Application application) {
        LoginDataBase db = LoginDataBase.getDatabase(application);
        loginDao = db.loginDoa();
        allData = loginDao.getDetails();
    }

    public static LoginRepository getInstance(Application application) {
        if (instance == null) {
            instance = new LoginRepository(application);
        }
        return instance;
    }

    public Result<LoggedInUser> login(String username, String password) {
        if (allData != null) {
            for (LoginTable table : Objects.requireNonNull(allData.getValue())) {
                if (table.getUsername().equals(username)) {
                    if (password.equals(table.getPassword())) {
                        return new Result.Success<>(new LoggedInUser(Integer.toString(table.getId()), table.getUsername()));
                    } else {
                        return new Result.Error(new Exception("Wrong password"));
                    }
                }
            }
        }
        LoginTable data = new LoginTable();
        data.setUsername(username);
        data.setPassword(password);
        insertData(data);
        return new Result.Success<>(new LoggedInUser(Integer.toString(data.getId()), username));
    }

    public void insertData(LoginTable... data) {
        loginDao.deleteAllData();
        loginDao.insertDetails(data[0]);
    }

}