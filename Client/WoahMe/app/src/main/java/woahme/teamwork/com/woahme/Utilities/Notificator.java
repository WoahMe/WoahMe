package woahme.teamwork.com.woahme.Utilities;

import android.app.Notification;
import android.content.Context;
import br.com.goncalves.pugnotification.notification.PugNotification;
import woahme.teamwork.com.woahme.R;

public final class Notificator {
    public static void Notificate(Context context, String title, String message)
    {
        PugNotification.with(context)
                .load()
                .title(title)
                .message(message)
                .smallIcon(R.drawable.woahme)
                .largeIcon(R.drawable.woahme)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();
    }
}
