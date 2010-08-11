/**
 *  Copyright (C) 2010 Cloud.com, Inc.  All rights reserved.
 * 
 * This software is licensed under the GNU General Public License v3 or later.
 * 
 * It is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.cloud.api.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.cloud.api.BaseCmd;
import com.cloud.api.ServerApiException;
import com.cloud.user.Account;
import com.cloud.utils.Pair;

public class AddConfigCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(AddConfigCmd.class.getName());

    private static final String s_name = "addconfigresponse";
    private static final List<Pair<Enum, Boolean>> s_properties = new ArrayList<Pair<Enum, Boolean>>();

    static {
    	s_properties.add(new Pair<Enum, Boolean>(BaseCmd.Properties.INSTANCE, Boolean.TRUE));
    	s_properties.add(new Pair<Enum, Boolean>(BaseCmd.Properties.COMPONENT, Boolean.TRUE));
    	s_properties.add(new Pair<Enum, Boolean>(BaseCmd.Properties.CATEGORY, Boolean.TRUE));
    	s_properties.add(new Pair<Enum, Boolean>(BaseCmd.Properties.NAME, Boolean.TRUE));
    	s_properties.add(new Pair<Enum, Boolean>(BaseCmd.Properties.VALUE, Boolean.FALSE));
    	s_properties.add(new Pair<Enum, Boolean>(BaseCmd.Properties.DESCRIPTION, Boolean.FALSE));
    }
    @Override
    public String getName() {
        return s_name;
    }
    
    @Override
    public List<Pair<Enum, Boolean>> getProperties() {
        return s_properties;
    }

    @Override
    public List<Pair<String, Object>> execute(Map<String, Object> params) {
    	String instance = (String) params.get(BaseCmd.Properties.INSTANCE.getName());
    	String component = (String) params.get(BaseCmd.Properties.COMPONENT.getName()); 
    	String category = (String) params.get(BaseCmd.Properties.CATEGORY.getName());
    	String name = (String) params.get(BaseCmd.Properties.NAME.getName());
    	String value = (String) params.get(BaseCmd.Properties.VALUE.getName());
    	String description = (String) params.get(BaseCmd.Properties.DESCRIPTION.getName());
    	    	
		try 
		{
			boolean status = getManagementServer().addConfig(instance, component, category, name, value, description);
			List<Pair<String, Object>> returnValues = new ArrayList<Pair<String, Object>>();
			
			if(status)
			{	
				returnValues.add(new Pair<String, Object>(BaseCmd.Properties.NAME.getName(), name));
				returnValues.add(new Pair<String, Object>(BaseCmd.Properties.VALUE.getName(), value));
			}
            
            return returnValues;
		}
		catch (Exception ex) {
			s_logger.error("Exception adding config value: ", ex);
			throw new ServerApiException(BaseCmd.INTERNAL_ERROR, "Failed to add config value : " + ex.getMessage());
		}

    }
}
