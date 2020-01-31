/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package qiangyt.springboot_example.server.security;

import javax.annotation.PostConstruct;
import org.springframework.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import qiangyt.springboot_example.common.misc.Delegation;

/**
 *
 * Based on org.springframework.security.config.authentication.CachingUserDetailsService
 * 
 */
@Getter
@Setter
@Component
public class CachingUserDetailsService 
extends Delegation<UserDetailsService>
implements UserDetailsService {

    @Autowired
	private CacheManager cacheManager;
	
	private UserCache userCache;
	
	public CachingUserDetailsService(@Autowired UserDetailsServiceImpl target) {
		super(target);

		//setUserCache(new NullUserCache());
	}

	@PostConstruct
	public void init() throws Exception {
        var userCache = new SpringCacheBasedUserCache(getCacheManager().getCache("jwt-cache"));
        setUserCache(userCache);
	}


	public UserDetails loadUserByUsername(String userName) {
		var u = getUserCache().getUserFromCache(userName);

		if (u == null) {
			u = getTarget().loadUserByUsername(userName);
		}

		getUserCache().putUserInCache(u);
		return u;
	}
}
