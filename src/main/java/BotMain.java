import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


import javax.security.auth.login.LoginException;

public class BotMain extends ListenerAdapter {
    public static void main(String[] args)throws LoginException{
        JDABuilder builder=new JDABuilder(AccountType.BOT);
        String token="NTYyMjA3MTQ5NDM5MDU3OTMx.XKObIQ.V6c_gcwBB2gD4QxSsklVMA3pbtE";
        builder.setToken(token);
        builder.addEventListeners(new EventBot());
        builder.addEventListeners(new GuildEvent());
        builder.addEventListeners(new ModifyRole());
        builder.addEventListeners(new ManageMembers());
        builder.addEventListeners(new WelcomMember());
        builder.build();




        }


}

