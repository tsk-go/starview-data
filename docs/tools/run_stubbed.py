# Local-only runner: stubs the compiled modules (msgq, params) this x86 box lacks, then runs pytest.
import sys, types
for name in ["openpilot.cereal.messaging", "openpilot.cereal.services", "openpilot.common.params", "openpilot.common.swaglog"]:
  m = types.ModuleType(name)
  sys.modules[name] = m
sys.modules["openpilot.cereal.services"].SERVICE_LIST = {}
sys.modules["openpilot.common.params"].Params = object
sys.modules["openpilot.common.params"].ParamKeyType = types.SimpleNamespace()
class _Log:
  def __getattr__(self, k): return lambda *a, **kw: None
sys.modules["openpilot.common.swaglog"].cloudlog = _Log()
import pytest
sys.exit(pytest.main(sys.argv[1:]))
