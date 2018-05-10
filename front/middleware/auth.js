export default function (context) {
  if (!context.store.state.auth.authenticated) {
    return context.redirect('/checklist/login')
  }
}

