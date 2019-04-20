import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import sun.audio.AudioPlayer;

import javax.security.auth.login.LoginException;

public class BotMain extends ListenerAdapter {
    public static void main(String[] args)throws LoginException{
        JDABuilder builder=new JDABuilder(AccountType.BOT);
        String token="NTYyMjA3MTQ5NDM5MDU3OTMx.XKObIQ.V6c_gcwBB2gD4QxSsklVMA3pbtE";
        builder.setToken(token);
        builder.addEventListener(new EventBot());
        builder.addEventListener(new GuildEvent());
        builder.addEventListener(new ModifyRole());
        builder.buildAsync();




        }


}

