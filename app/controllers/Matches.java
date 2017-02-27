package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.toJson;

/**
 * Matches page controller
 */

@Security.Authenticated(Secured.class)
public class Matches extends Controller {


    public Result index() {
        return ok(views.html.matches.render(User.findByEmail(request().username())));
    }

    //TODO
    public Result getMatchedList() {
        List<User> matchedUsers = new ArrayList<>();
        User user_1 = User.findByEmail("qvuong2007@gmail.com");
        User user_2 = User.findByEmail("admin@gmail.com");
        matchedUsers.add(0, user_1);
        matchedUsers.add(1, user_2);
        return ok(toJson(matchedUsers));
    }

    //TODO
    public Result getWaitingList() {
        List<User> waitingReplyFromUsers = new ArrayList<>();
        //fake data
        User fake3 = new User();
        fake3.fullname = "Fake Three";
        fake3.email = "fake3@gmail.com";
        fake3.id = 103L;

        User fake4 = new User();
        fake4.fullname = "Fake Four";
        fake4.email = "fake4@gmail.com";
        fake4.id = 104L;
        waitingReplyFromUsers.add(0, fake3);
        waitingReplyFromUsers.add(1, fake4);

        return ok(toJson(waitingReplyFromUsers));
    }

    //TODO
    public Result getNewSuggestionMatches() {
        List<User> newMatches = new ArrayList<>();
        //fake data
        User fake5 = new User();
        fake5.fullname = "Fake Five";
        fake5.email = "fake5@gmail.com";
        fake5.id = 105L;
        User fake6 = new User();
        fake6.fullname = "Fake Six";
        fake6.email = "fake6@gmail.com";
        fake6.id = 106L;
        newMatches.add(0, fake5);
        newMatches.add(1, fake6);

        User fake7 = new User();
        fake7.fullname = "Fake Seven";
        fake7.email = "fake7@gmail.com";
        fake7.id = 107L;
        User fake8 = new User();
        fake8.fullname = "Fake Eight";
        fake8.email = "fake8@gmail.com";
        fake8.id = 108L;
        User fake9 = new User();
        fake9.fullname = "Fake Nine";
        fake9.email = "fake9@gmail.com";
        fake9.id = 109L;
        User fake10 = new User();
        fake10.fullname = "Fake Ten";
        fake10.email = "fake10@gmail.com";
        fake10.id = 110L;
        newMatches.add(2, fake7);
        newMatches.add(3, fake8);
        newMatches.add(4, fake9);
        newMatches.add(5, fake10);
        return ok(toJson(newMatches));
    }
}
