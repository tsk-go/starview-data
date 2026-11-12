# Commits

- Every commit is authored and committed as `tsk-go <cmc686@gmail.com>`.
- Never mention Claude or Anthropic in commits: no `Co-Authored-By` trailer, no session links, no attribution lines.
- Every commit is signed with the owner's SSH signing key so GitHub shows it as Verified - the same key as
  tsk-go/openpilot: private key in the environment variable `GIT_SIGNING_KEY_B64`, fingerprint
  `SHA256:ituxS1NO/VJowXXhvOv/nUdnZQYSQkHbkSL4fQIDC2E`. The cloud environment's global git config signs with its own
  key (`gpg.ssh.program /tmp/code-sign`), which GitHub shows as Unverified, so this repo needs its own config.
- `.claude/hooks/session-start.sh` sets that up at every cloud session start (SessionStart hook in
  `.claude/settings.json`). Its message in the session context says whether it worked. If it didn't run (this repo was
  added to a session that started in another repo), run it by hand: `CLAUDE_CODE_REMOTE=true .claude/hooks/session-start.sh`.
- Before every commit, check `git config gpg.ssh.program` is `ssh-keygen` and `ssh-keygen -lf ~/.ssh/claude_signing.pub`
  prints that fingerprint; after it, check `git log -1 --format=%GK` shows it too. If the key is missing or doesn't
  match, the hook blocks commits here (`gpg.ssh.program false`): don't work around it, don't commit or push - tell the
  owner.
