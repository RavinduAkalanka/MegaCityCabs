package com.bms.dao;

import com.bms.model.User;

public interface UserDAO {
	User findUserByEmail(String email);
}
