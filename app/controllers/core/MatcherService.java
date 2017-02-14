package controllers.core;

import models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuongnguyen on 14/02/2017.
 *
 * Core service that supports Match Controller.
 * It provides 3 methods:
 * + Agree: List of users that agreed on match request
 * + Waiting: List of users that haven't responded to match request
 * + Matcher: list of all matching user to the current one
 */

public class MatcherService {

    public List<User> getAgreedUsers() {
        return new ArrayList<User>();
    }

    public List<User> getWaitingUsers() {
        return new ArrayList<User>();
    }

    public List<User> getMatcherUsers() {
        return new ArrayList<User>();
    }
}
