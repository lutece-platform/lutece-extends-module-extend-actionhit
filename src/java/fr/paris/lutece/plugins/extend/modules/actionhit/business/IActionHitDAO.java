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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 * Interface of ActionHitDAO
 */
public interface IActionHitDAO
{
    /**
     * Create an action hit
     * @param actionHit Action hit to save
     * @param plugin The plugin
     */
    void create( ActionHit actionHit, Plugin plugin );

    /**
     * Update an action hit
     * @param actionHit The action hit to update
     * @param plugin The plugin
     */
    void updateHit( ActionHit actionHit, Plugin plugin );

    /**
     * Delete an action hit
     * @param nIdActionHit The if of the action hit to delete
     * @param plugin The plugin
     */
    void delete( int nIdActionHit, Plugin plugin );

    /**
     * Delete action hits associated with a given resource
     * @param strExtendableResourceType The type of the resource to remove hits
     *            of
     * @param strIdExtendableResource The id of the resource to remove hits of
     * @param plugin The plugin
     */
    void deleteByResource( String strExtendableResourceType, String strIdExtendableResource, Plugin plugin );

    /**
     * Find every action hit
     * @param plugin The plugin
     * @return The list of action hits
     */
    List<ActionHit> findAll( Plugin plugin );

    /**
     * Get the list of action hits associated to a given action and a given
     * resource type
     * @param strActionName The name of the action to get the hits of
     * @param strExtendableResourceType The resource type
     * @param plugin The plugin
     * @return The list of action hits, or an empty list if no actions was found
     */
    List<ActionHit> findActionHitsByAction( String strActionName, String strExtendableResourceType, Plugin plugin );

    /**
     * Get an action hit from its id
     * @param strActionName The name of the action to get
     * @param strIdExtendableResource The id of the resource to get the action
     *            of
     * @param strExtendableResourceType The type of the resource to get the
     *            action of
     * @param plugin The plugin
     * @return The action hit, or null if no action hit has the given name or is
     *         associated to the given resource
     */
    ActionHit findByActionNameAndResource( String strActionName, String strIdExtendableResource,
        String strExtendableResourceType, Plugin plugin );

    /**
     * Get the list of action hits associated with a given resource.
     * @param strExtendableResourceType The resource type of hits
     * @param strIdExtendableResource The id of the extendable resource to get
     *            hits of
     * @param plugin The plugin
     * @return The list of action hits
     */
    List<ActionHit> findAllByResource( String strExtendableResourceType, String strIdExtendableResource, Plugin plugin );
}
