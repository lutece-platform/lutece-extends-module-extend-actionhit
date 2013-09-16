DROP TABLE IF EXISTS extend_actionhit_action_hit;
CREATE TABLE extend_actionhit_action_hit (
	id_action_hit INT NOT NULL,
	action_name VARCHAR(100) NOT NULL,
	id_extendable_resource VARCHAR(255) NOT NULL,
	extendable_resource_type VARCHAR(255) NOT NULL,
	hit INT NOT NULL,
	PRIMARY KEY (id_action_hit)
);