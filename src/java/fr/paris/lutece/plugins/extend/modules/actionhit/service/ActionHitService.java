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
package fr.paris.lutece.plugins.extend.modules.actionhit.service;

import fr.paris.lutece.plugins.extend.modules.actionhit.business.ActionHit;
import fr.paris.lutece.plugins.extend.modules.actionhit.business.ActionHitHome;
import fr.paris.lutece.portal.service.cache.AbstractCacheableService;

import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.InitializingBean;

import java.util.ArrayList;
import java.util.List;


/**
 * ActionbarService
 */
public class ActionHitService extends AbstractCacheableService implements InitializingBean
{
    /**
     * Bean name
     */
    public static final String BEAN_NAME = "extend-actionhit.actionHitService";
    private static final String SERVICE_NAME = "action Hit Cache Service";
    private static final String CONSTANT_UNDERSCORE = "_";

    /**
     * Creates an action hit
     * @param actionHit The action hit to create
     */
    public void createActionHit( ActionHit actionHit )
    {
        ActionHitHome.create( actionHit );
        putInCache( getCacheKeyFromResource( actionHit.getActionName(  ), actionHit.getIdExtendableResource(  ),
                actionHit.getExtendableResourceType(  ) ), actionHit );
    }

    /**
     * find an action hit
     * @param strActionName The name of the action to get the number of hit of
     * @param strIdExtendableResource The id of the extendable resource to get
     *            the number of hits of
     * @param strExtendableResourceType The extendable resource type of the
     *            resource to get the number of hits of
     * @return The action hit, or null if no action hit has this id
     */
    public ActionHit findActionHit( String strActionName, String strIdExtendableResource,
        String strExtendableResourceType )
    {
        String strCacheKey = getCacheKeyFromResource( strActionName, strIdExtendableResource, strExtendableResourceType );
        ActionHit actionHit = (ActionHit) getFromCache( strCacheKey );

        if ( actionHit != null )
        {
            return actionHit;
        }

        actionHit = ActionHitHome.findByActionNameAndResource( strActionName, strIdExtendableResource,
                strExtendableResourceType );
        putInCache( strCacheKey, actionHit );

        return actionHit;
    }

    /**
     * Get the list of action hits associated with a given resource.
     * @param strResourceType The resource type of hits
     * @param strIdExtendableResource The id of the extendable resource to get
     *            hits of
     * @return The list of action hits
     */
    public List<ActionHit> findActionHitsByResource( String strResourceType, String strIdExtendableResource )
    {
        return ActionHitHome.findAllByResource( strResourceType, strIdExtendableResource );
    }

    /**
     * Get the list of action hits associated to a given action and a given
     * resource type
     * @param strActionName The name of the action to get the hits of
     * @param strExtendableResourceType The resource type
     * @return The list of action hits, or an empty list if no actions was found
     */
    public List<ActionHit> findActionHitsByAction( String strActionName, String strExtendableResourceType )
    {
        return ActionHitHome.findActionHitsByAction( strActionName, strExtendableResourceType );
    }

    /**
     * Get the list of action hits
     * @return The list of action hits
     */
    public List<ActionHit> findAllActionHits(  )
    {
        return ActionHitHome.findAll(  );
    }

    /**
     * Update an action hit
     * @param actionHit The action hit to update
     */
    public void updateActionHit( ActionHit actionHit )
    {
        ActionHitHome.updateHit( actionHit );
        putInCache( getCacheKeyFromResource( actionHit.getActionName(  ), actionHit.getIdExtendableResource(  ),
                actionHit.getExtendableResourceType(  ) ), actionHit );
    }

    /**
     * Remove an action hit from its id
     * @param strActionName The name of the action to remove
     * @param strIdExtendableResource The id of the extendable resource to
     *            remove
     * @param strExtendableResourceType The extendable resource type of the
     *            resource to remove
     */
    public void removeActionHit( String strActionName, String strIdExtendableResource, String strExtendableResourceType )
    {
        removeActionHit( ActionHitHome.findByActionNameAndResource( strActionName, strIdExtendableResource,
                strExtendableResourceType ) );
        removeKey( getCacheKeyFromResource( strActionName, strIdExtendableResource, strExtendableResourceType ) );
    }

    /**
     * Remove an action hit from its id
     * @param actionHit The action hit to remove
     */
    public void removeActionHit( ActionHit actionHit )
    {
        ActionHitHome.delete( actionHit );
        removeKey( getCacheKeyFromResource( actionHit.getActionName(  ), actionHit.getIdExtendableResource(  ),
                actionHit.getExtendableResourceType(  ) ) );
    }

    /**
     * Remove action hits associated with a given resource
     * @param strExtendableResourceType The type of the resource to remove hits
     *            of
     * @param strIdExtendableResource The id of the resource to remove hits of
     */
    public void removeByResource( String strExtendableResourceType, String strIdExtendableResource )
    {
        ActionHitHome.deleteByResource( strExtendableResourceType, strIdExtendableResource );

        String strKeySuffixe = getCacheKeyWithoutAction( strIdExtendableResource, strExtendableResourceType );
        List<String> listRemovedKeys = new ArrayList<String>(  );

        for ( String strKey : getKeys(  ) )
        {
            if ( StringUtils.contains( strKey, strKeySuffixe ) )
            {
                listRemovedKeys.add( strKey );
            }
        }

        for ( String strKey : listRemovedKeys )
        {
            removeKey( strKey );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(  )
    {
        return SERVICE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet(  ) throws Exception
    {
        initCache(  );
    }

    /**
     * Get the key in the cache of an action hit
     * @param strActionName The action name
     * @param strIdExtendableResource The id of the resource
     * @param strExtendableResourceType The type of the resource
     * @return The key of the action hit in the cache
     */
    private String getCacheKeyFromResource( String strActionName, String strIdExtendableResource,
        String strExtendableResourceType )
    {
        StringBuilder sbKey = new StringBuilder( strActionName );
        sbKey.append( CONSTANT_UNDERSCORE ).append( strIdExtendableResource ).append( CONSTANT_UNDERSCORE )
             .append( strExtendableResourceType );

        return sbKey.toString(  );
    }

    /**
     * Get the key in the cache of a resource, without the starting action name.
     * This method should only be used to check if a cache key is associated to
     * a given resource
     * @param strIdExtendableResource The id of the extendable resource
     * @param strExtendableResourceType The type of the extendable resource
     * @return The suffixe of the key of the cache
     */
    private String getCacheKeyWithoutAction( String strIdExtendableResource, String strExtendableResourceType )
    {
        StringBuilder sbKey = new StringBuilder( CONSTANT_UNDERSCORE );
        sbKey.append( strIdExtendableResource ).append( CONSTANT_UNDERSCORE ).append( strExtendableResourceType );

        return sbKey.toString(  );
    }
}
