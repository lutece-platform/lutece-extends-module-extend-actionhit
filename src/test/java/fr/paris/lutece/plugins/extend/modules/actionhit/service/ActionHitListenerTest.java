/*
 * Copyright (c) 2002-2026, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.extend.modules.actionhit.service;

import fr.paris.lutece.plugins.extend.modules.actionhit.business.ActionHitHome;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;

import jakarta.inject.Inject;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ActionHitListenerTest
 */
public class ActionHitListenerTest extends LuteceTestCase
{
    private static final String RESOURCE_TYPE = "ACTIONHIT_LISTENER_TEST";
    private static final String ID_RESOURCE_1 = "1";
    private static final String ID_RESOURCE_2 = "2";
    private static final String ACTION_DOWNLOAD = "download";
    private static final String ACTION_VIEW = "view";

    @Inject
    private ActionHitListener _listener;

    @Inject
    private ActionHitService _actionHitService;

    @Inject
    private ActionHitExtendableResourceRemovalListener _removalListener;

    private boolean _bWasInstalled;

    /**
     * Installs the module for the test.
     */
    @BeforeEach
    public void installPlugin( )
    {
        Plugin plugin = PluginService.getPlugin( ActionHitHome.PLUGIN_NAME );
        _bWasInstalled = plugin.isInstalled( );
        plugin.install( );
    }

    /**
     * Removes the action hits of the test resources.
     */
    @AfterEach
    public void removeActionHits( )
    {
        Plugin plugin = PluginService.getPlugin( ActionHitHome.PLUGIN_NAME );

        if ( !plugin.isInstalled( ) )
        {
            plugin.install( );
        }

        _actionHitService.removeByResource( RESOURCE_TYPE, ID_RESOURCE_1 );
        _actionHitService.removeByResource( RESOURCE_TYPE, ID_RESOURCE_2 );

        if ( !_bWasInstalled )
        {
            plugin.uninstall( );
        }
    }

    /**
     * Counts the notified actions per resource and per action.
     */
    @Test
    public void testNotifyAndCount( )
    {
        assertEquals( 0, _listener.getResourceActionHit( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD ), "No hit before any action" );

        _listener.notifyActionOnResource( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD );
        _listener.notifyActionOnResource( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD );
        _listener.notifyActionOnResource( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_VIEW );
        _listener.notifyActionOnResource( ID_RESOURCE_2, RESOURCE_TYPE, ACTION_DOWNLOAD );

        assertEquals( 2, _listener.getResourceActionHit( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD ), "Two downloads of the first resource" );
        assertEquals( 3, _listener.getActionHit( ACTION_DOWNLOAD, RESOURCE_TYPE ), "Three downloads of the resource type" );

        Map<String, Integer> mapHits = _listener.getResourceHit( ID_RESOURCE_1, RESOURCE_TYPE );
        assertEquals( 2, mapHits.size( ), "Two actions on the first resource" );
        assertEquals( 1, mapHits.get( ACTION_VIEW ), "One view of the first resource" );
    }

    /**
     * Removes the hits of a removed resource and only those.
     */
    @Test
    public void testRemovalListener( )
    {
        _listener.notifyActionOnResource( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD );
        _listener.notifyActionOnResource( ID_RESOURCE_2, RESOURCE_TYPE, ACTION_DOWNLOAD );

        _removalListener.doRemoveResourceExtentions( RESOURCE_TYPE, ID_RESOURCE_1 );

        assertTrue( _listener.getResourceHit( ID_RESOURCE_1, RESOURCE_TYPE ).isEmpty( ), "The removed resource has no hit left" );
        assertEquals( 1, _listener.getResourceActionHit( ID_RESOURCE_2, RESOURCE_TYPE, ACTION_DOWNLOAD ), "The other resource keeps its hit" );
    }

    /**
     * Counts nothing and keeps the data while the module is not installed.
     */
    @Test
    public void testPluginDisabled( )
    {
        _listener.notifyActionOnResource( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD );
        PluginService.getPlugin( ActionHitHome.PLUGIN_NAME ).uninstall( );

        _listener.notifyActionOnResource( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD );
        _removalListener.doRemoveResourceExtentions( RESOURCE_TYPE, ID_RESOURCE_1 );
        assertEquals( 0, _listener.getResourceActionHit( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD ), "A disabled module answers no hit" );

        PluginService.getPlugin( ActionHitHome.PLUGIN_NAME ).install( );
        assertEquals( 1, _listener.getResourceActionHit( ID_RESOURCE_1, RESOURCE_TYPE, ACTION_DOWNLOAD ), "Only the hit notified while installed is kept" );
    }
}
