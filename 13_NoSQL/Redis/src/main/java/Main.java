import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class Main {
    // Запуск докер-контейнера:
    // docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis

    // Для теста будем считать неактивными пользователей, которые не заходили 2 секунды
    private static final int DELETE_SECONDS_AGO = 2;

    // Допустим пользователи делают 500 запросов к сайту в секунду
    private static final int RPS = 500;

    // И всего на сайт заходило 1000 различных пользователей
    private static final int USERS = 1000;


    // Также мы добавим задержку между посещениями
    private static final int SLEEP = 100; // 1 миллисекунда

    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");

    private static void log(int UsersOnline) {
        String log = String.format("[%s] Пользователей онлайн: %d", DF.format(new Date()), UsersOnline);
        out.println(log);
    }

    public static void main(String[] args) throws InterruptedException {

        RedisStorage redis = new RedisStorage();
        redis.init();
        // создаем 20 пользователей
        for (int i = 1; i <= 20; i++) {
            redis.initUsers(i);

        }

        int i = 0;
        int random = new Random().nextInt(9) + 1;

        for (;;) {
            // показ пользователей
            redis.printFirstUser();
            if (i == 10){
                i = 0;
                random = new Random().nextInt(9) + 1;
            }
            i++;
            Thread.sleep(SLEEP);

            if (i == random){
                redis.logPageVisit(new Random().nextInt(10));
            }

        }


    }

}
