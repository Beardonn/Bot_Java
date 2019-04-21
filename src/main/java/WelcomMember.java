import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import sun.plugin2.message.Message;

public class WelcomMember extends ListenerAdapter {
    private Member member;
    private String memberName;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent joinEvent){

        memberName=joinEvent.getMember().getUser().getAsMention();
        member=joinEvent.getGuild().getMembersByName(memberName,true).get(0);
        joinEvent.getGuild().getTextChannelById("564114733368606720").sendMessage("witaj na kanale "+ memberName+" wpisz !komendy by zobaczyc moje dostepne komendy").queue();

    }

}
