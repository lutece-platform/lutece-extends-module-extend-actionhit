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

import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * DAO to manage ActionHit
 */
@ApplicationScoped
public class ActionHitDAO implements IActionHitDAO
{
    private static final String SQL_QUERY_INSERT = " INSERT INTO extend_actionhit_action_hit ( action_name, id_extendable_resource, extendable_resource_type, hit ) VALUES (?,?,?,?) ";
    private static final String SQL_QUERY_SELECTALL = " SELECT id_action_hit, action_name, id_extendable_resource, extendable_resource_type, hit FROM extend_actionhit_action_hit ";
    private static final String SQL_QUERY_SELECT_BY_ACTION_NAME_AND_RESOURCE = SQL_QUERY_SELECTALL +
        " WHERE action_name = ? AND id_extendable_resource = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_SELECT_BY_RESOURCE = SQL_QUERY_SELECTALL +
        " WHERE id_extendable_resource = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_SELECT_BY_ACTION_NAME = SQL_QUERY_SELECTALL +
        " WHERE action_name = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_DELETE = " DELETE FROM extend_actionhit_action_hit WHERE id_action_hit = ? ";
    private static final String SQL_QUERY_DELETE_BY_RESOURCE = " DELETE FROM extend_actionhit_action_hit WHERE id_extendable_resource = ? AND extendable_resource_type = ? ";
    private static final String SQL_QUERY_UPDATE = " UPDATE extend_actionhit_action_hit SET hit = ? WHERE id_action_hit = ? ";
    private static final String SQL_QUERY_INCREMENT = " UPDATE extend_actionhit_action_hit SET hit = hit + 1 WHERE id_action_hit = ? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void create( ActionHit actionHit, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, Statement.RETURN_GENERATED_KEYS, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setString( nIndex++, actionHit.getActionName(  ) );
            daoUtil.setString( nIndex++, actionHit.getIdExtendableResource(  ) );
            daoUtil.setString( nIndex++, actionHit.getExtendableResourceType(  ) );
            daoUtil.setInt( nIndex, actionHit.getHit(  ) );
            daoUtil.executeUpdate(  );

            if ( daoUtil.nextGeneratedKey(  ) )
            {
                actionHit.setIdActionHit( daoUtil.getGeneratedKeyInt( 1 ) );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionHit findByActionNameAndResource( String strActionName, String strIdExtendableResource,
        String strExtendableResourceType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ACTION_NAME_AND_RESOURCE, plugin ) )
        {
            daoUtil.setString( 1, strActionName );
            daoUtil.setString( 2, strIdExtendableResource );
            daoUtil.setString( 3, strExtendableResourceType );
            daoUtil.executeQuery(  );

            ActionHit actionHit = null;

            if ( daoUtil.next(  ) )
            {
                actionHit = getActionHitFromDaoUtil( daoUtil );
            }

            return actionHit;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateHit( ActionHit actionHit, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, plugin ) )
        {
            daoUtil.setInt( 1, actionHit.getHit(  ) );
            daoUtil.setInt( 2, actionHit.getIdActionHit(  ) );
            daoUtil.executeUpdate(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementHit( int nIdActionHit, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INCREMENT, plugin ) )
        {
            daoUtil.setInt( 1, nIdActionHit );
            daoUtil.executeUpdate(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdActionHit, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, plugin ) )
        {
            daoUtil.setInt( 1, nIdActionHit );
            daoUtil.executeUpdate(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteByResource( String strExtendableResourceType, String strIdExtendableResource, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_BY_RESOURCE, plugin ) )
        {
            daoUtil.setString( 1, strIdExtendableResource );
            daoUtil.setString( 2, strExtendableResourceType );
            daoUtil.executeUpdate(  );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionHit> findAll( Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECTALL, plugin ) )
        {
            daoUtil.executeQuery(  );

            return getActionHitsFromDaoUtil( daoUtil );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionHit> findAllByResource( String strExtendableResourceType, String strIdExtendableResource,
        Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_RESOURCE, plugin ) )
        {
            daoUtil.setString( 1, strIdExtendableResource );
            daoUtil.setString( 2, strExtendableResourceType );
            daoUtil.executeQuery(  );

            return getActionHitsFromDaoUtil( daoUtil );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionHit> findActionHitsByAction( String strActionName, String strExtendableResourceType, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_BY_ACTION_NAME, plugin ) )
        {
            daoUtil.setString( 1, strActionName );
            daoUtil.setString( 2, strExtendableResourceType );
            daoUtil.executeQuery(  );

            return getActionHitsFromDaoUtil( daoUtil );
        }
    }

    /**
     * Read every remaining row of an executed query
     * @param daoUtil The daoUtil to read
     * @return The list of action hits
     */
    private List<ActionHit> getActionHitsFromDaoUtil( DAOUtil daoUtil )
    {
        List<ActionHit> listActionHit = new ArrayList<>(  );

        while ( daoUtil.next(  ) )
        {
            listActionHit.add( getActionHitFromDaoUtil( daoUtil ) );
        }

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
