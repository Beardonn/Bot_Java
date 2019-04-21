import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.time.Instant;


public class ModifyRole extends ListenerAdapter {
    private Member member;
    private Role role;
    private User user;
    private String[] message;
    private Role rolacheck;
    private Member membercheck;
    private Role permission;
    private Role roleExist;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent g){
        message=g.getMessage().getContentRaw().split(" "); //0 komenda 1 kto 2 rola
        user=g.getMessage().getAuthor(); //zgarniamy autora
        member=Methods.getMemberName(g,message); //zgarniamy imie podanego uzytkownika
        permission=Methods.checkSenderRole(g,"Moderator"); //sprawdzamy czy podany wczesniej uzytkownik ma taka role nadana

        rolacheck=g.getGuild().getRoles().stream().filter(p->p.getName().equalsIgnoreCase(message[2])).findAny().orElse(null); //patrzymy czy istnieje podana rola na serwerze
        membercheck=g.getGuild().getMembers().stream().filter(m->m.getUser().getName().equalsIgnoreCase(message[1])).findAny().orElse(null); //patrzymy czy dany uzytkownik jest w ogole na serwerze
        roleExist=Methods.checkMemberRole(g,member,message[2]); //do sprawdzenia czy dany gosc ma podana role juz nadana


        if(message[0].equals("!rola")) {
            if (permission==null) //uprawnienia
            {
                Methods.log("Nie masz uprawnien do tej komendy",g);
            }
            else if (rolacheck==null) //czy istnieje rola (ktora chcemy nadac)
            {
                Methods.log("Nie ma takiej roli, wpisz !rolacreate by stworzyc",g);
            }else if(membercheck==null) //czy jest podany uzytkownik
            {
                Methods.log("Nie ma takiego uzytkownika na tym serwerze",g);
            }else{
                addRole(message, g);
                embedbuilder(g,message,user,"Dodano");
                Methods.log("Nadano role "+message[2]+" uzytkownikowi "+message[1], g);
            }

        }
        if(message[0].equals("!removerola")){
            if (permission==null) //uprawnienia
            {
                Methods.log("Nie masz uprawnien do tej komendy",g);
            }else if (roleExist==null) //czy podany uzytkownik ma taka role czy nie
            {
                Methods.log("Uzytkownik nie posiada takiej roli",g);
            }else{
            removeRole(message,g);
            embedbuilder(g,message,user,"Odebrano");
            Methods.log("Odebrano role "+message[2]+" uzytkownikowi "+message[1],g);
        }
        }
    }
    /*public void log(String log_message,GuildMessageReceivedEvent g){
        g.getChannel().sendMessage(log_message).queue();

    }*/
    private void addRole(String[] args, GuildMessageReceivedEvent g){
        member=g.getGuild().getMembersByName(args[1],true).get(0);
        role= g.getGuild().getRolesByName(args[2],true).get(0);
        g.getGuild().getController().addRolesToMember(member, role).queue();

    }
    private void removeRole(String[] args, GuildMessageReceivedEvent g){
        member=g.getGuild().getMembersByName(args[1],true).get(0);
        role= g.getGuild().getRolesByName(args[2],true).get(0);
        g.getGuild().getController().removeRolesFromMember(member,role).queue();

    }
    public void createRole(String[] args, GuildMessageReceivedEvent g){
        g.getGuild().getController().createRole().setName(args[1]).setPermissions(Permission.MANAGE_PERMISSIONS,Permission.NICKNAME_CHANGE,Permission.CREATE_INSTANT_INVITE,Permission.MESSAGE_READ,Permission.MESSAGE_WRITE).queue();

    }
    private void embedbuilder(GuildMessageReceivedEvent g, String[] args, User user, String operacja){
        EmbedBuilder builder=new EmbedBuilder();
        builder.setAuthor(user.getName());
        builder.setColor(Color.BLUE);
        builder.setTitle(operacja+" role");
        builder.setDescription(operacja+" role "+args[2] +" uzytkownikowi "+args[1]);
        builder.setTimestamp(Instant.now());
        g.getGuild().getTextChannelById("564114733368606720").sendMessage(builder.build()).queue();
    }


}
