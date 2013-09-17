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
            nHits += actionHit.getHit( );
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
        Map<String, Integer> resourceHit = new HashMap<String, Integer>( listActionsHits.size( ) );
        for ( ActionHit actionHit : listActionsHits )
        {
            resourceHit.put( actionHit.getActionName( ), actionHit.getHit( ) );
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
        return actionHit != null ? actionHit.getHit( ) : 0;
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
            actionHit.setHit( actionHit.getHit( ) + 1 );
            _actionHitService.updateActionHit( actionHit );
        }
    }
}
