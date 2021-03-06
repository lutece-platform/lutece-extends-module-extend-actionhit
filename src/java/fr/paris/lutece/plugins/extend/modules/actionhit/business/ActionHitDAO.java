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
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * DAO to manage ActionHit
 */
public class ActionHitDAO implements IActionHitDAO
{
    private static final String SQL_QUERY_CREATE_ACTION_HIT = " INSERT INTO extend_actionhit_action_hit ( id_action_hit, action_name, id_extendable_resource, extendable_resource_type, hit ) VALUES (?,?,?,?,?) ";
    private static final String SQL_QUERY_NEW_PRIMARY_KEY = " SELECT MAX(id_action_hit) + 1 FROM extend_actionhit_action_hit ";
    private static final String SQL_QUERY_FIND_ALL = " SELECT id_action_hit, action_name, id_extendable_resource, extendable_resource_type, hit FROM extend_actionhit_action_hit ";
    private static final String SQL_QUERY_FIND_BY_ACTION_NAME_AND_RESOURCE = SQL_QUERY_FIND_ALL +
        " WHERE action_name = ? AND id_extendable_resource = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_FIND_ALL_BY_RESOURCE = SQL_QUERY_FIND_ALL +
        " WHERE id_extendable_resource = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_FIND_ALL_BY_ACTION_NAME = SQL_QUERY_FIND_ALL +
        " WHERE action_name = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_DELETE_ACTION_HIT = " DELETE FROM extend_actionhit_action_hit WHERE id_action_hit = ? ";
    private static final String SQL_QUERY_DELETE_ACTION_HIT_BY_RESOURCE = " DELETE FROM extend_actionhit_action_hit WHERE id_extendable_resource = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_UPDATE_ACTION_HIT = " UPDATE extend_actionhit_action_hit SET hit = ? WHERE id_action_hit = ? ";

    /**
     * Get a new value for the primary key
     * @param plugin The plugin
     * @return The new primary key
     */
    private int getNewPrimaryKey( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PRIMARY_KEY, plugin );
        daoUtil.executeQuery(  );

        int nRes = 1;

        if ( daoUtil.next(  ) )
        {
            nRes = daoUtil.getInt( 1 );
        }

        daoUtil.free(  );

        return nRes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void create( ActionHit actionHit, Plugin plugin )
    {
        actionHit.setIdActionHit( getNewPrimaryKey( plugin ) );

        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_CREATE_ACTION_HIT, plugin );
        daoUtil.setInt( 1, actionHit.getIdActionHit(  ) );
        daoUtil.setString( 2, actionHit.getActionName(  ) );
        daoUtil.setString( 3, actionHit.getIdExtendableResource(  ) );
        daoUtil.setString( 4, actionHit.getExtendableResourceType(  ) );
        daoUtil.setInt( 5, actionHit.getHit(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHit findByActionNameAndResource( String strActionName, String strIdExtendableResource,
        String strExtendableResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_ACTION_NAME_AND_RESOURCE, plugin );
        daoUtil.setString( 1, strActionName );
        daoUtil.setString( 2, strIdExtendableResource );
        daoUtil.setString( 3, strExtendableResourceType );
        daoUtil.executeQuery(  );

        ActionHit actionHit = null;

        if ( daoUtil.next(  ) )
        {
            actionHit = getActionHitFromDaoUtil( daoUtil );
        }

        daoUtil.free(  );

        return actionHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateHit( ActionHit actionHit, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_ACTION_HIT, plugin );
        daoUtil.setInt( 1, actionHit.getHit(  ) );
        daoUtil.setInt( 2, actionHit.getIdActionHit(  ) );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdActionHit, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_ACTION_HIT, plugin );
        daoUtil.setInt( 1, nIdActionHit );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    @Override
    public void deleteByResource( String strExtendableResourceType, String strIdExtendableResource, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_ACTION_HIT_BY_RESOURCE, plugin );
        daoUtil.setString( 1, strIdExtendableResource );
        daoUtil.setString( 2, strExtendableResourceType );
        daoUtil.executeUpdate(  );
        daoUtil.free(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionHit> findAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ALL, plugin );
        List<ActionHit> listActionHit = new ArrayList<ActionHit>(  );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            listActionHit.add( getActionHitFromDaoUtil( daoUtil ) );
        }

        daoUtil.free(  );

        return listActionHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionHit> findAllByResource( String strExtendableResourceType, String strIdExtendableResource,
        Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ALL_BY_RESOURCE, plugin );
        daoUtil.setString( 1, strIdExtendableResource );
        daoUtil.setString( 2, strExtendableResourceType );

        List<ActionHit> listActionHit = new ArrayList<ActionHit>(  );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            listActionHit.add( getActionHitFromDaoUtil( daoUtil ) );
        }

        daoUtil.free(  );

        return listActionHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionHit> findActionHitsByAction( String strActionName, String strExtendableResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_ALL_BY_ACTION_NAME, plugin );
        daoUtil.setString( 1, strActionName );
        daoUtil.setString( 2, strExtendableResourceType );

        List<ActionHit> listActionHit = new ArrayList<ActionHit>(  );
        daoUtil.executeQuery(  );

        while ( daoUtil.next(  ) )
        {
            listActionHit.add( getActionHitFromDaoUtil( daoUtil ) );
        }

        daoUtil.free(  );

        return listActionHit;
    }

    /**
     * Get an action hit from a DAOUTil
     * @param daoUtil The daoUtil to get data from
     * @return The action action
     */
    private ActionHit getActionHitFromDaoUtil( DAOUtil daoUtil )
    {
        ActionHit actionHit = new ActionHit(  );
        int nIndex = 1;
        actionHit.setIdActionHit( daoUtil.getInt( nIndex++ ) );
        actionHit.setActionName( daoUtil.getString( nIndex++ ) );
        actionHit.setIdExtendableResource( daoUtil.getString( nIndex++ ) );
        actionHit.setExtendableResourceType( daoUtil.getString( nIndex++ ) );
        actionHit.setHit( daoUtil.getInt( nIndex ) );

        return actionHit;
    }
}
