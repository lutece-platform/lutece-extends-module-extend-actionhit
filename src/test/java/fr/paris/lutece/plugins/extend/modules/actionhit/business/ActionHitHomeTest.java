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
package fr.paris.lutece.plugins.extend.modules.actionhit.business;

import fr.paris.lutece.test.LuteceTestCase;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * ActionHitHomeTest
 */
public class ActionHitHomeTest extends LuteceTestCase
{
    private static final String RESOURCE_TYPE = "ACTIONHIT_HOME_TEST";
    private static final String ID_RESOURCE_1 = "1";
    private static final String ID_RESOURCE_2 = "2";
    private static final String ACTION_DOWNLOAD = "download";
    private static final String ACTION_VIEW = "view";

    /**
     * Removes the action hits of the test resources.
     */
    @AfterEach
    public void removeActionHits( )
    {
        ActionHitHome.deleteByResource( RESOURCE_TYPE, ID_RESOURCE_1 );
        ActionHitHome.deleteByResource( RESOURCE_TYPE, ID_RESOURCE_2 );
    }

    /**
     * Creates an action hit, reads it back, updates and deletes it.
     */
    @Test
    public void testCrud( )
    {
        ActionHit actionHit = new ActionHit( ACTION_DOWNLOAD, ID_RESOURCE_1, RESOURCE_TYPE, 1 );
        ActionHitHome.create( actionHit );

        ActionHit stored = ActionHitHome.findByActionNameAndResource( ACTION_DOWNLOAD, ID_RESOURCE_1, RESOURCE_TYPE );
        assertNotNull( stored, "The created action hit is found" );
        assertEquals( actionHit.getIdActionHit( ), stored.getIdActionHit( ), "The generated id is set on the created object" );
        assertEquals( 1, stored.getHit( ), "The hit count is stored" );

        stored.setHit( 7 );
        ActionHitHome.updateHit( stored );
        assertEquals( 7, ActionHitHome.findByActionNameAndResource( ACTION_DOWNLOAD, ID_RESOURCE_1, RESOURCE_TYPE ).getHit( ), "The hit count is updated" );

        ActionHitHome.delete( stored );
        assertNull( ActionHitHome.findByActionNameAndResource( ACTION_DOWNLOAD, ID_RESOURCE_1, RESOURCE_TYPE ), "The action hit is deleted" );
    }

    /**
     * Creates two action hits and checks that each one gets its own id.
     */
    @Test
    public void testGeneratedIds( )
    {
        ActionHit first = new ActionHit( ACTION_DOWNLOAD, ID_RESOURCE_1, RESOURCE_TYPE, 1 );
        ActionHit second = new ActionHit( ACTION_VIEW, ID_RESOURCE_1, RESOURCE_TYPE, 1 );
        ActionHitHome.create( first );
        ActionHitHome.create( second );

        assertTrue( first.getIdActionHit( ) != second.getIdActionHit( ), "Two action hits get two ids" );
    }

    /**
     * Checks the finders by resource and by action, and the removal by resource.
     */
    @Test
    public void testFindersAndDeleteByResource( )
    {
        ActionHitHome.create( new ActionHit( ACTION_DOWNLOAD, ID_RESOURCE_1, RESOURCE_TYPE, 2 ) );
        ActionHitHome.create( new ActionHit( ACTION_VIEW, ID_RESOURCE_1, RESOURCE_TYPE, 3 ) );
        ActionHitHome.create( new ActionHit( ACTION_DOWNLOAD, ID_RESOURCE_2, RESOURCE_TYPE, 4 ) );

        List<ActionHit> listByResource = ActionHitHome.findAllByResource( RESOURCE_TYPE, ID_RESOURCE_1 );
        assertEquals( 2, listByResource.size( ), "Two actions on the first resource" );

        List<ActionHit> listByAction = ActionHitHome.findActionHitsByAction( ACTION_DOWNLOAD, RESOURCE_TYPE );
        assertEquals( 2, listByAction.size( ), "Two resources hit by the download action" );

        ActionHitHome.deleteByResource( RESOURCE_TYPE, ID_RESOURCE_1 );
        assertTrue( ActionHitHome.findAllByResource( RESOURCE_TYPE, ID_RESOURCE_1 ).isEmpty( ), "The first resource has no hit left" );
        assertEquals( 1, ActionHitHome.findAllByResource( RESOURCE_TYPE, ID_RESOURCE_2 ).size( ), "The second resource keeps its hit" );
    }
}
