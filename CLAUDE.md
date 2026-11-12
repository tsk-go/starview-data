# Commits

- Every commit is authored and committed as `tsk-go <cmc686@gmail.com>`.
- Never mention Claude or Anthropic in commits: no `Co-Authored-By` trailer, no session links, no attribution lines.
- Every commit is signed with one of the owner's two SSH signing keys - no other key - so GitHub shows it as Verified.
  Both are Signing Keys on the tsk-go account, the same two as tsk-go/openpilot:
  - main key: private key in the environment variable `GIT_SIGNING_KEY_B64` (Claude Code cloud sessions), written to
    `~/.ssh/claude_signing`; fingerprint `SHA256:ituxS1NO/VJowXXhvOv/nUdnZQYSQkHbkSL4fQIDC2E`.
  - "StarView chat" key: `~/.ssh/chat_signing` in the owner's claude.ai StarView chat workspace (no environment
    variables there); fingerprint `SHA256:98d19r36BEJtwbf8sddMlMqnjrmEf1dEHMqNcT3u9ww`.
  The cloud environment's global git config signs with its own key (`gpg.ssh.program /tmp/code-sign`), which GitHub
  shows as Unverified, so this repo needs its own config.
- `.claude/hooks/session-start.sh` sets that up at every cloud session start (SessionStart hook in
  `.claude/settings.json`): the main key when `GIT_SIGNING_KEY_B64` is set, else `~/.ssh/chat_signing` if it is
  exactly the chat key; `~/.ssh/allowed_signers` holds both. Its message in the session context says which key it
  uses. If it didn't run (this repo was added to a session that started in another repo, or the chat), run it by hand:
  `CLAUDE_CODE_REMOTE=true .claude/hooks/session-start.sh`.
- Before every commit, check `git config gpg.ssh.program` is `ssh-keygen` and `ssh-keygen -lf` of the key in
  `git config user.signingkey` prints one of the two fingerprints; after it, check `git log -1 --format=%GK` shows
  that same one. If neither key is there, or a key doesn't match, the hook blocks commits here
  (`gpg.ssh.program false`): don't work around it, don't commit or push - tell the owner.
