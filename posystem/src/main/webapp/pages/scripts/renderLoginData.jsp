<%@ page import="br.valinorti.executor.HibernateExecutor" %>
<%@ page import="br.valinorti.posystem.command.*" %>
<%@ page import="br.valinorti.posystem.entity.*" %>
<%
	String login = request.getUserPrincipal().getName();
	HibernateExecutor<User> executor = new HibernateExecutor<User>();
	User user = executor.executeCommand(new GetUserByLoginCommand(login));
	String subscriberId = String.valueOf(user.getSubscriber().getId());
%>
dojo.setObject("subscriber",<%= subscriberId %>);		