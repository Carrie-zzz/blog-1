# InitializingBean 接口 #
	 afterPropertiesSet();
1. spring 初始化bean时候 先调用afterPropertiesSet
2. 然后 调用 init-method方法


# HandlerAdapter #
1. 在InitializingBean接口 方法中
	1. 初始化	initControllerAdviceCache()   @ControllerAdvice
		1. @ModelAttribute
		2. @InitBinder
		3. @ExceptionHandle
	2. 初始化	argumentResolvers
		1. 24个默认的参数解析器
			1. @RequestParam
			2. @PathVariable
		2. 自定义解析器  custom
			
		
	3. 初始化	initBinderArgumentResolvers
	4. 初始化	returnValueHandlers