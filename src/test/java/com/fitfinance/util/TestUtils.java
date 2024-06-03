package com.fitfinance.util;

import org.awaitility.Awaitility;

import java.time.Duration;
import java.time.Instant;

public class TestUtils {
    public static void waitFor(Duration duration) {
        final Instant startTime = Instant.now();
        Awaitility
                .await()
                .atMost(duration.plus(Duration.ofSeconds(1)))
                .with()
                .pollInterval(duration)
                .until(() -> {
                    Duration elapsed = Duration.between(Instant.now(), startTime);
                    return elapsed.abs().compareTo(duration) >= 0;
                });
    }
}
