package com.terminalbit.spongy.util;

import com.google.common.base.Predicate;
import org.spongepowered.api.Game;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.permission.context.Context;
import org.spongepowered.api.util.Tristate;

import java.util.Set;

class Permissions {
    public static final String PERM_CHAT_RECEIVE = "chatterbox.chat.receive";
    public static final String PERM_CHAT = "chatterbox.chat.send";
    public static final String PERM_ME = "chatterbox.command.me";
    public static final String PERM_RELOAD = "chatterbox.command.reload";
    public static final String PERM_EDIT = "chatterbox.command.edit";
    public static final String PERM_MUTE = "chatterbox.command.mute";
    public static final String PERM_UNMUTE = "chatterbox.command.unmute";
	public static final String PERM_SPAWN = "spongy.tele.spawn";
    public static final String PERM_WARP = "chatterbox.chat.send";
    public static final String PERM_NICK = "spongy.chat.nick";
    public static final String PERM_ME = "spongy.chat.me";

    static void registerDefaultPermissions(Game game) {
        game.getServiceManager().potentiallyProvide(PermissionService.class).executeWhenPresent(new Predicate<PermissionService>() {
            @Override
            public boolean apply(PermissionService input) {
                final SubjectData defaultData = input.getDefaultData();
                defaultData.setPermission(SubjectData.GLOBAL_CONTEXT, PERM_CHAT_RECEIVE, Tristate.TRUE);
                defaultData.setPermission(SubjectData.GLOBAL_CONTEXT, PERM_CHAT, Tristate.TRUE);
                defaultData.setPermission(SubjectData.GLOBAL_CONTEXT, PERM_ME, Tristate.TRUE);
                return true;
            }
        });
    }

    static boolean togglePermission(Subject subject, Set<Context> contexts, String permission) {
        return true;
    }

}