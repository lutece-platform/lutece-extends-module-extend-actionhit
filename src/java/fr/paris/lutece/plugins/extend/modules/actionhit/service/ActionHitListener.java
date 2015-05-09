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
import fr.paris.lutece.portal.service.resource.IExtendableResourceActionHitListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Action hit listener
 */
public class ActionHitListener implements IExtendableResourceActionHitListener
{
    @Inject
    @Named( ActionHitService.BEAN_NAME )
    private ActionHitService _actionHitService;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getActionHit( String strActionName, String strExtendableResourceType )
    {
        List<ActionHit> listActionsHits = _actionHitService.findActionHitsByAction( strActionName,
                strExtendableResourceType );
        int nHits = 0;

        for ( ActionHit actionHit : listActionsHits )
        {
            nHits += actionHit.getHit(  );
        }

        return nHits;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Integer> getResourceHit( String strExtendableResourceId, String strExtendableResourceType )
    {
        List<ActionHit> listActionsHits = _actionHitService.findActionHitsByResource( strExtendableResourceType,
                strExtendableResourceId );
        Map<String, Integer> resourceHit = new HashMap<String, Integer>( listActionsHits.size(  ) );

        for ( ActionHit actionHit : listActionsHits )
        {
            resourceHit.put( actionHit.getActionName(  ), actionHit.getHit(  ) );
        }

        return resourceHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResourceActionHit( String strExtendableResourceId, String strExtendableResourceType,
        String strActionName )
    {
        ActionHit actionHit = _actionHitService.findActionHit( strActionName, strExtendableResourceId,
                strExtendableResourceType );

        return ( actionHit != null ) ? actionHit.getHit(  ) : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyActionOnResource( String strExtendableResourceId, String strExtendableResourceType,
        String strActionName )
    {
        ActionHit actionHit = _actionHitService.findActionHit( strActionName, strExtendableResourceId,
                strExtendableResourceType );

        if ( actionHit == null )
        {
            actionHit = new ActionHit( strActionName, strExtendableResourceId, strExtendableResourceType, 1 );
            _actionHitService.createActionHit( actionHit );
        }
        else
        {
            actionHit.setHit( actionHit.getHit(  ) + 1 );
            _actionHitService.updateActionHit( actionHit );
        }
    }
}
