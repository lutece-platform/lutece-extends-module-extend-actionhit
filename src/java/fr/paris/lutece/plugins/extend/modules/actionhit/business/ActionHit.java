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

import java.io.Serializable;


/**
 * Class that count the number of times an action is performed over a resource
 */
public class ActionHit implements Serializable
{
    /**
     * ActionHit resource type
     */
    public static final String RESOURCE_TYPE = "ACTION_HIT";
    
    private static final long serialVersionUID = 1L;
    
    private int _nIdActionHit;
    private String _strActionName;
    private String _strExtendableResourceType;
    private String _strIdExtendableResource;
    private int _nHit;

    /**
     * Default constructor
     */
    public ActionHit(  )
    {
    }

    /**
     * Creates an action hit
     * @param strActionName The name of the action
     * @param strIdExtendableResource The id of the extendable resource
     * @param strExtendableResourceType The type of the extendable resource
     * @param nHit The number of hits
     */
    public ActionHit( String strActionName, String strIdExtendableResource, String strExtendableResourceType, int nHit )
    {
        _strActionName = strActionName;
        _strIdExtendableResource = strIdExtendableResource;
        _strExtendableResourceType = strExtendableResourceType;
        _nHit = nHit;
    }

    /**
     * Get the id of the action hit
     * @return The id of the action hit
     */
    public int getIdActionHit(  )
    {
        return _nIdActionHit;
    }

    /**
     * Set the id of the action hit
     * @param nIdActionHit The id of the action hit
     */
    public void setIdActionHit( int nIdActionHit )
    {
        _nIdActionHit = nIdActionHit;
    }

    /**
     * Get the name of the action
     * @return The name of the action
     */
    public String getActionName(  )
    {
        return _strActionName;
    }

    /**
     * Set the name of the action
     * @param strActionName The name of the action
     */
    public void setActionName( String strActionName )
    {
        _strActionName = strActionName;
    }

    /**
     * Get the resource type associated with this action hit
     * @return The resource type associated with this action hit
     */
    public String getExtendableResourceType(  )
    {
        return _strExtendableResourceType;
    }

    /**
     * Set the resource type associated with this action hit
     * @param strExtendableResourceType The resource type associated with this
     *            action hit
     */
    public void setExtendableResourceType( String strExtendableResourceType )
    {
        _strExtendableResourceType = strExtendableResourceType;
    }

    /**
     * Get the id of the extendable resource
     * @return The id of the extendable resource
     */
    public String getIdExtendableResource(  )
    {
        return _strIdExtendableResource;
    }

    /**
     * Set the id of the extendable resource
     * @param strIdExtendableResource The id of the extendable resource
     */
    public void setIdExtendableResource( String strIdExtendableResource )
    {
        _strIdExtendableResource = strIdExtendableResource;
    }

    /**
     * Get the number of hits
     * @return The number of hits
     */
    public int getHit(  )
    {
        return _nHit;
    }

    /**
     * Set the number of hits
     * @param nHit The number of hits
     */
    public void setHit( int nHit )
    {
        _nHit = nHit;
    }
}
