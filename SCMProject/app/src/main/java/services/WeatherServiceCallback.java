package services;

import data.Channel;

/**
 * Created by rsantos on 12/16/16.
 */

public interface WeatherServiceCallback {
    void serviceSuccess(Channel channel);
    void serviceFailed(Exception exception);
}
