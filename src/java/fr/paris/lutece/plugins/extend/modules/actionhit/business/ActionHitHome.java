/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.extend.modules.actionhit.business;

import fr.paris.lutece.plugins.extend.modules.actionhit.service.ActionHitPlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;


/**
 * Home for ActionHit management
 */
public final class ActionHitHome
{
    private static IActionHitDAO _dao = SpringContextService.getBean( "extend-actionhit.actionHitDAO" );
    private static Plugin _plugin;

    /**
     * Private constructor
     */
    private ActionHitHome(  )
    {
    }

    /**
     * Create an action hit
     * @param actionHit Action hit to save
     */
    public static void create( ActionHit actionHit )
    {
        _dao.create( actionHit, getPlugin(  ) );
    }

    /**
     * Get an action hit from its id
     * @param strActionName The name of the action to get
     * @param strIdExtendableResource The id of the resource to get the action
     *            of
     * @param strExtendableResourceType The type of the resource to get the
     *            action of
     * @return The action hit, or null if no action hit has the given name or is
     *         associated to the given resource
     */
    public static ActionHit findByActionNameAndResource( String strActionName, String strIdExtendableResource,
        String strExtendableResourceType )
    {
        return _dao.findByActionNameAndResource( strActionName, strIdExtendableResource, strExtendableResourceType,
            getPlugin(  ) );
    }

    /**
     * Update an action hit
     * @param actionHit The action hit to update
     */
    public static void updateHit( ActionHit actionHit )
    {
        _dao.updateHit( actionHit, getPlugin(  ) );
    }

    /**
     * Delete an action hit
     * @param actionHit The action hit to delete
     */
    public static void delete( ActionHit actionHit )
    {
        _dao.delete( actionHit.getIdActionHit(  ), getPlugin(  ) );
    }

    /**
     * Delete an action hit
     * @param nIdActionHit The id of the action hit to delete
     */
    public static void delete( int nIdActionHit )
    {
        _dao.delete( nIdActionHit, getPlugin(  ) );
    }

    /**
     * Remove action hits associated with a given resource
     * @param strExtendableResourceType The type of the resource to remove hits
     *            of
     * @param strIdExtendableResource The id of the resource to remove hits of
     */
    public static void deleteByResource( String strExtendableResourceType, String strIdExtendableResource )
    {
        _dao.deleteByResource( strExtendableResourceType, strIdExtendableResource, getPlugin(  ) );
    }

    /**
     * Find every action hit
     * @return The list of action hits
     */
    public static List<ActionHit> findAll(  )
    {
        return _dao.findAll( getPlugin(  ) );
    }

    /**
     * Get the list of action hits associated with a given resource.
     * @param strExtendableResourceType The resource type of hits
     * @param strIdExtendableResource The id of the extendable resource to get
     *            hits of
     * @return The list of action hits
     */
    public static List<ActionHit> findAllByResource( String strExtendableResourceType, String strIdExtendableResource )
    {
        return _dao.findAllByResource( strExtendableResourceType, strIdExtendableResource, getPlugin(  ) );
    }

    /**
     * Get the list of action hits associated to a given action and a given
     * resource type
     * @param strActionName The name of the action to get the hits of
     * @param strExtendableResourceType The resource type
     * @return The list of action hits, or an empty list if no actions was found
     */
    public static List<ActionHit> findActionHitsByAction( String strActionName, String strExtendableResourceType )
    {
        return _dao.findActionHitsByAction( strActionName, strExtendableResourceType, getPlugin(  ) );
    }

    /**
     * Get the action hit plugin
     * @return The action hit plugin
     */
    private static Plugin getPlugin(  )
    {
        if ( _plugin == null )
        {
            _plugin = PluginService.getPlugin( ActionHitPlugin.PLUGIN_NAME );
        }

        return _plugin;
    }
}
