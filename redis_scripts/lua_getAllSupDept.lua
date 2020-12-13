-- Redis Lua脚本：查询指定部门的所有上级部门，公众号 zxiaofan
-- 脚本保存为 lua_getAllSupDept.lua；

--[[
	luaScriptName: getAllSupDept;
	function: get all Super Dept of currentDeptNo;
	auther: zxiaofan.com;
param:
	rediskey: the key of redis, the data structures is Hashes;
	currentDeptNo: the current DeptNo;
	utilDeptNo: query super dept until the utilDeptNo;
	maxGetTimes: the max times of query to prevent dead loop.
result:
	a. the whole super detp data;
	b. the super detp data but not until specified dept(utilDeptNo);
	c. return currentDeptNo when no data;
	d. return error "error: the times of query exceeded the maxGetTimes!";
	--]]

local rediskey = KEYS[1];
local currentDeptNo = KEYS[2];
local utilDeptNo = KEYS[3];
local maxGetTimes = tonumber(KEYS[4]);

-- redis.debug("rediskey: %q",rediskey);
-- redis.debug("currentDeptNo: %q",currentDeptNo);
-- redis.debug("utilDeptNo: %q",utilDeptNo);
-- redis.debug("maxGetTimes: %q",maxGetTimes);


	if(currentDeptNo == utilDeptNo) then
		return currentDeptNo;
	end

	if(maxGetTimes > 100) then
		maxGetTimes = 100;
	end

	local time = 1;

	local result = currentDeptNo;
	local tempDept = currentDeptNo;

	while(tempDept ~= utilDeptNo)
	do
		if(time > maxGetTimes) then
			return "error: the times of query exceeded the maxGetTimes!";
		end

		tempDept = redis.call('hget',rediskey , tempDept);
		-- redis.debug("tempDept: %q",tempDept);

		if(tempDept == false or tempDept == "NULL") then
			return result;
		end

		result = result .. "," .. tempDept;
		time = time + 1 ;
	end

	return result;
