/**
 * "Eventy" web application
 * This is RESt web service for events management.
 * There is possibility to create new one, join or edit existing events, find future, actual and previous events.
 * author: Marlena Łukomska-Rogala
 */

package pl.net.rogala.eventy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventyApplication.class, args);
    }

}
