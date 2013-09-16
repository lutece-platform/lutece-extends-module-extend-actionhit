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
