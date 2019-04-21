import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.TimeUnit;

class Methods {
    static void log(String log_message, GuildMessageReceivedEvent g){
        g.getChannel().sendMessage(log_message).complete().delete().queueAfter(15,TimeUnit.SECONDS);

    }
    static Member getMemberName(GuildMessageReceivedEvent g, String[] message){
        return g.getGuild().getMembersByName(message[1],true).get(0);
    }
    static Role checkSenderRole(GuildMessageReceivedEvent g,String rola){
        return g.getMessage().getMember().getRoles().stream().filter(r->r.getName().equalsIgnoreCase(rola)).findAny().orElse(null);

    }
    static Role checkMemberRole(GuildMessageReceivedEvent g ,Member member, String message){
        return member.getRoles().stream().filter(m->m.getName().equalsIgnoreCase(message)).findAny().orElse(null);
    }
}
