// 把环境给到reader和scanner
context.setEnvironment(environment);
// 把所有的Converters 给到context里的beanFactory的ConversionService
		postProcessApplicationContext(context);
		// 在new StandServletEnvironment的时候初始化了7个初始化器，这一步循环这7个初始化器，往上下文里加进来一堆listener和processor
		applyInitializers(context);
		// 监听ApplicationContextInitializedEvent事件，从 new StandServletEnvironment 时候初始化的9个listener里取出来 BackgroundPreinitializer 和 DelegatingApplicationListener 执行，其实啥也没干
		listeners.contextPrepared(context);
		// 啥也没干
		bootstrapContext.close(context);
		if (this.logStartupInfo) {
			logStartupInfo(context.getParent() == null);
			// 打印启动的关于Profile的日志
			logStartupProfileInfo(context);
		}
		// 取出来上下文的beanFactory（DefaultListableBeanFactory）
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		beanFactory.registerSingleton("springApplicationArguments", applicationArguments);
		if (printedBanner != null) {
			// 注册这个 springBootBanner
			beanFactory.registerSingleton("springBootBanner", printedBanner);
		}
		if (beanFactory instanceof DefaultListableBeanFactory) {
			((DefaultListableBeanFactory) beanFactory)
					.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
		}
		if (this.lazyInitialization) {
			context.addBeanFactoryPostProcessor(new LazyInitializationBeanFactoryPostProcessor());
		}
		// 取出来主类（DataApplication）
		Set<Object> sources = getAllSources();
		Assert.notEmpty(sources, "Sources must not be empty");
		// 把名字是主类的 AnnotatedGenericBeanDifinition 放到beanFactory 的 beanDefinitionMap 里和 beanDefinitionNames 里，这个 AnnotatedGenericBeanDifinition 的元数据 metadata 里有对这个类的描述，比如有什么注解
		load(context, sources.toArray(new Object[0]));
		// 针对事件 ApplicationPreparedEvent 选出来 
		// EnvironmentPostProcessorApplicationListener，这个弄了一个DeferredLogs，不知道是干嘛
		// LoggingApplicationListener 往beanFactory的 singletonObjects、registeredSingleTons 里加了一个 springBootLoggingSystem(默认是 LogbackLoggingSystem)，和 springBootLoggerGroups(web和sql)
		// BackgroundPreinitializer、啥也没干
		// DelegatingApplicationListener，啥也没干
		listeners.contextLoaded(context);