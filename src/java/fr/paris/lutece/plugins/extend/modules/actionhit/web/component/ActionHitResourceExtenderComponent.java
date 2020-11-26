/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.extend.modules.actionhit.web.component;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.paris.lutece.plugins.extend.business.extender.ResourceExtenderDTO;
import fr.paris.lutece.plugins.extend.modules.actionhit.business.ActionHit;
import fr.paris.lutece.plugins.extend.modules.actionhit.service.ActionHitService;
import fr.paris.lutece.plugins.extend.util.JSONUtils;
import fr.paris.lutece.plugins.extend.web.component.NoConfigResourceExtenderComponent;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * ActionHitResourceExtenderComponent
 *
 */
public class ActionHitResourceExtenderComponent extends NoConfigResourceExtenderComponent
{
    // TEMPLATES
    private static final String TEMPLATE_ACTION_HIT = "skin/plugins/extend/modules/actionhit/action_hit.html";
    private static final String MARK_ACTION_HIT = "action_hit";
    private static final String JSON_KEY_ACTION_NAME = "actionName";

    // SERVICES
    @Inject
    private ActionHitService _actionHitService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void buildXmlAddOn( String strIdExtendableResource, String strExtendableResourceType, String strParameters,
        StringBuffer strXml )
    {
        // Nothing yet
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPageAddOn( String strIdExtendableResource, String strExtendableResourceType, String strParameters,
        HttpServletRequest request )
    {
        // Method to get the html code of the extension in front office
        ActionHit actionsHit;
        String strActionName = getActionNameFromParameters( strParameters );

        // If there is no action name, we return an empty string
        if ( strActionName == null )
        {
            return StringUtils.EMPTY;
        }

        actionsHit = _actionHitService.findActionHit( strActionName, strIdExtendableResource, strExtendableResourceType );

        if ( actionsHit == null )
        {
            // If no action hit was found, we create a new one with 0 hit 
            actionsHit = new ActionHit( strActionName, strIdExtendableResource, strExtendableResourceType, 0 );
        }

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( MARK_ACTION_HIT, actionsHit );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_ACTION_HIT, request.getLocale(  ), model );

        return template.getHtml(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getInfoHtml( ResourceExtenderDTO resourceExtender, Locale locale, HttpServletRequest request )
    {
        return StringUtils.EMPTY;
    }

    /**
     * Get the action name from extender parameter
     * @param strParameters The parameters of the extender
     * @return The action name, or null if no action name was found
     */
    private String getActionNameFromParameters( String strParameters )
    {
        ObjectNode jsonParameters = JSONUtils.parseParameters( strParameters );
        String strActionName = null;

        if ( jsonParameters != null )
        {
            if ( jsonParameters.has( JSON_KEY_ACTION_NAME ) )
            {
                strActionName = jsonParameters.get( JSON_KEY_ACTION_NAME ).asText( );
            }
            else 
            {
                AppLogService.debug( "No " + JSON_KEY_ACTION_NAME + " found in " + jsonParameters );
            }
        }

        return strActionName;
    }
}
